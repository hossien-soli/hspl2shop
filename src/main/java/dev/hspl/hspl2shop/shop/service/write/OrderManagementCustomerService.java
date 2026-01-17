package dev.hspl.hspl2shop.shop.service.write;

import dev.hspl.hspl2shop.common.ApplicationUser;
import dev.hspl.hspl2shop.common.component.ApplicationAttributeProvider;
import dev.hspl.hspl2shop.common.component.ApplicationUuidGenerator;
import dev.hspl.hspl2shop.common.component.DomainEventPublisher;
import dev.hspl.hspl2shop.common.exception.AccountStateException;
import dev.hspl.hspl2shop.common.exception.UserRoleAccessException;
import dev.hspl.hspl2shop.common.model.UserAddressDto;
import dev.hspl.hspl2shop.common.value.*;
import dev.hspl.hspl2shop.shop.component.PaymentWebServiceAgent;
import dev.hspl.hspl2shop.shop.component.dto.PaymentRequestResult;
import dev.hspl.hspl2shop.shop.event.SessionMarkedAsPaidEvent;
import dev.hspl.hspl2shop.shop.exception.order.*;
import dev.hspl.hspl2shop.shop.model.write.entity.*;
import dev.hspl.hspl2shop.shop.model.write.repository.*;
import dev.hspl.hspl2shop.shop.service.dto.OrderItemInfoDto;
import dev.hspl.hspl2shop.shop.service.dto.OrderRegistrationDto;
import dev.hspl.hspl2shop.shop.value.*;
import dev.hspl.hspl2shop.user.UserModuleApi;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NullMarked;
import org.springframework.core.retry.RetryPolicy;
import org.springframework.core.retry.RetryTemplate;
import org.springframework.resilience.annotation.Retryable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@NullMarked
public class OrderManagementCustomerService {
    private final ApplicationUuidGenerator uuidGenerator;
    private final OrderRepository orderRepository;
    private final DeliveryMethodRepository deliveryMethodRepository;
    private final PaymentWebServiceAgent paymentWebServiceAgent;
    private final UserModuleApi userModuleApi;
    private final ApplicationAttributeProvider attributeProvider;
    private final ProductRepository productRepository;
    private final PaymentSessionRepository paymentSessionRepository;
    private final StockChangeRepository stockChangeRepository;
    private final DomainEventPublisher eventPublisher;
    private final OrderAddressRepository orderAddressRepository;

    public UUID registerNewCustomerOrder(ApplicationUser user, OrderRegistrationDto data) {
        if (!user.role().equals(UserRole.CUSTOMER)) {
            throw new UserRoleAccessException(UserAction.REGISTER_NEW_CUSTOMER_ORDER, user.role());
        }

        if (!user.isAccountActive()) {
            throw new AccountStateException(UserAction.REGISTER_NEW_CUSTOMER_ORDER);
        }

        var currentDateTime = LocalDateTime.now();

        // TODO: add delay between each order registration for user

        UserAddressDto addressDto = userModuleApi
                .findUserAddressById(user.id(), data.userAddressId())
                .orElseThrow(() -> new AddressNotFoundOrderException(data.userAddressId()));

        var deliveryMethod = deliveryMethodRepository.findForOrderReadOnly(data.deliveryMethodId())
                .orElseThrow(() -> new DeliveryMethodNotFoundOrderException(data.deliveryMethodId()));

        if (!deliveryMethod.active()) {
            throw new InactiveDeliveryMethodOrderException(data.deliveryMethodId());
        }

        Set<ProductVariantId> productIds = data.items().stream().map(item -> new ProductVariantId(
                new HumanReadableId(item.productId()),
                item.variantIndex()
        )).collect(Collectors.toSet());

        List<ProductForOrder> products = productRepository.findAllForOrderByIds(productIds);

        short orderTotalItems = 0;

        short orderItemCount = 0;
        boolean c1, c2;

        long orderTotalWeightG = 0;

        int productDiscountAmount;
        long orderFinalPrice = 0;
        int totalDiscountAmount = 0;

        int dmDiscountAmount = 0;
        if (deliveryMethod.discountPercent() != null && deliveryMethod.discountPercent() >= 1) {
            dmDiscountAmount = (deliveryMethod.discountPercent() * deliveryMethod.basePrice()) / 100;
        }

        totalDiscountAmount += dmDiscountAmount;
        orderFinalPrice += deliveryMethod.basePrice() - dmDiscountAmount;
        // TODO: add more complex custom logic for computing delivery price

        short variantMaxItems = attributeProvider.orderProductVariantMaxItems();

        List<OrderItem> orderItems = new ArrayList<>(products.size());

        for (ProductForOrder product : products) {
            if (!product.isVisible()) {
                throw new InvisibleProductFoundOrderException(product.getId(), product.getProductName(),
                        product.getVariantName());
            }

            for (OrderItemInfoDto itemInfo : data.items()) {
                c1 = itemInfo.productId().equals(product.getId().productId().value());
                c2 = itemInfo.variantIndex() == product.getId().variantIndex();
                if (c1 && c2) {
                    orderItemCount = itemInfo.count();
                    // TODO: add here a "break;" carefully
                }
            }

            if (orderItemCount > product.getStockItems()) {
                throw new InsufficientStockItemOrderException(product.getId(), product.getProductName(),
                        product.getVariantName());
            }

            if (orderItemCount > variantMaxItems) {
                throw new VariantMaxItemLimitOrderException(product, orderItemCount, variantMaxItems);
            }

            orderItems.add(OrderItem.createItem(
                    product.getId(),
                    product.getPrice(),
                    product.getDiscountPercent(),
                    orderItemCount
            ));

            orderTotalItems += orderItemCount;
            orderTotalWeightG += (long) product.getWeight() * orderItemCount;

            productDiscountAmount = 0;
            if (product.getDiscountPercent() != null && product.getDiscountPercent() >= 1) {
                productDiscountAmount = (product.getDiscountPercent() * product.getPrice()) / 100;
            }

            orderFinalPrice += (long) (product.getPrice() - productDiscountAmount) * orderItemCount;
            totalDiscountAmount += productDiscountAmount * orderItemCount;
        }

        if (orderTotalItems <= 0) {
            throw new NoItemProvidedOrderException();
        }

        short orderMaxItems = attributeProvider.orderMaxItems();
        if (orderTotalItems > orderMaxItems) {
            throw new TooManyItemOrderRegistrationException(orderTotalItems, orderMaxItems);
        }

        short orderMaxWeightKG = attributeProvider.orderMaxWeightKG();
        if (orderTotalWeightG > orderMaxWeightKG * 1000) {
            throw new TooMuchWeightOrderException(orderTotalItems, orderTotalWeightG, orderMaxWeightKG);
        }

        if (orderTotalWeightG > deliveryMethod.maxWeightPossibleKG() * 1000) {
            throw new DeliveryMethodWeightLimitOrderException(
                    deliveryMethod.id(),
                    orderTotalItems, orderTotalWeightG,
                    deliveryMethod.maxWeightPossibleKG()
            );
        }

        long orderMaxPrice = attributeProvider.orderMaxPrice();
        if (orderFinalPrice > orderMaxPrice) {
            throw new BigPriceOrderRegistrationException(orderTotalItems, orderFinalPrice, orderMaxPrice);
        }

        UUID newOrderAddressId = uuidGenerator.generateNew();

        MapLocation mapLocation = null;
        if (addressDto.locationLat() != null && addressDto.locationLong() != null) {
            mapLocation = new MapLocation(addressDto.locationLat(), addressDto.locationLong());
        }

        OrderAddress orderAddress = OrderAddress.newAddress(
                newOrderAddressId,
                user.id(),
                new FullName(addressDto.deliveryFullName()),
                new PhoneNumber(addressDto.deliveryPhoneNumber()),
                addressDto.secondaryPhoneNumber() != null ? new PhoneNumber(addressDto.secondaryPhoneNumber()) : null,
                addressDto.provinceName(),
                addressDto.cityName(),
                new LiteralFullAddress(addressDto.literalFullAddress()),
                new PostalCode(addressDto.postalCode()),
                mapLocation
        );

        orderAddressRepository.save(orderAddress);

        UUID newOrderId = uuidGenerator.generateNew();

        OrderDeliveryInfo deliveryInfo = new OrderDeliveryInfo(
                deliveryMethod.id(),
                deliveryMethod.name(),
                deliveryMethod.basePrice(),
                deliveryMethod.discountPercent()
        );

        Order order = Order.newOrder(newOrderId, user.id(), orderItems, orderFinalPrice,
                totalDiscountAmount, deliveryInfo, newOrderAddressId, currentDateTime);

        orderRepository.save(order);

        return newOrderId;
    }

    private List<ProductForOrder> performProductCheckOnOrder(Order order) {
        Set<ProductVariantId> productIds = order.getItems().stream()
                .map(OrderItem::getProductVariantId).collect(Collectors.toSet());

        List<ProductForOrder> products = productRepository.findAllForOrderByIds(productIds);
        long visibleProductsCount = products.stream().filter(ProductForOrder::isVisible).count();
        int orderProductsCount = order.getItems().size();
        if (visibleProductsCount != orderProductsCount) {
            throw new OrderItemsSystemChangeException(order.getId(), orderProductsCount, visibleProductsCount);
        }

        Optional<ProductForOrder> checkResult = products.stream().filter(product -> {
            OrderItem orderItem = order.getItems().stream()
                    .filter(item -> item.getProductVariantId().equals(product.getId()))
                    .findFirst().orElseThrow();

            return orderItem.getCount() > product.getStockItems();
        }).findFirst();

        if (checkResult.isPresent()) {
            throw new InsufficientStockItemOrderException(checkResult.get());
        }

        return products;
    }

    public Object requestPaymentForOrder(ApplicationUser user, UUID orderId) {
        if (!user.role().equals(UserRole.CUSTOMER)) {
            throw new UserRoleAccessException(UserAction.CUSTOMER_REQUEST_PAYMENT_FOR_ORDER, user.role());
        }

        if (!user.isAccountActive()) {
            throw new AccountStateException(UserAction.CUSTOMER_REQUEST_PAYMENT_FOR_ORDER);
        }

        Order order = orderRepository.find(orderId)
                .orElseThrow(() -> new OrderNotFoundException(orderId));

        if (order.getUserId() == null || !order.getUserId().equals(user.id())) {
            throw new OrderOwnerException(orderId, order.getUserId(), user.id());
        }

        if (!order.getStatus().equals(OrderStatus.REGISTERED)) {
            throw new BadOrderStatusPaymentRequestException(order.getStatus());
        }

        var currentDateTime = LocalDateTime.now();

        short orderLifetimeMinutes = attributeProvider.orderLifetimeMinutes();
        long minutesElapsed = Math.abs(Duration.between(currentDateTime, order.getCreatedAt()).toMinutes());
        if (minutesElapsed >= orderLifetimeMinutes) {
            throw new OrderOutdatedException(order.getId(), minutesElapsed, orderLifetimeMinutes);
            // TODO: create scheduled job to mark orders as outdated
        }

        short checkDelaySeconds = attributeProvider.orderProductCheckDelaySeconds();
        long secondsElapsed = Math.abs(Duration.between(currentDateTime, order.getCreatedAt()).toSeconds());
        if (secondsElapsed >= checkDelaySeconds) {
            performProductCheckOnOrder(order);
        }

        String callbackUrl = attributeProvider.paymentGatewayCallbackUrl();

        paymentSessionRepository.findUserLastSessionCreatedAt(user.id()).ifPresent(lastSession -> {
            short delayBetweenSessions = attributeProvider.delayLimitBetweenPaymentSessions();
            long secondsElapsed2 = Math.abs(Duration.between(currentDateTime, lastSession).toSeconds());
            if (secondsElapsed2 < delayBetweenSessions) {
                throw new PaymentSessionLimitationException(delayBetweenSessions, secondsElapsed2);
            }
        });

        PaymentRequestResult result = paymentWebServiceAgent
                .requestNewPayment(user.phoneNumber(), callbackUrl, order.getPaymentPrice());

        UUID newSessionId = uuidGenerator.generateNew();

        PaymentSession newSession = PaymentSession.newSession(
                newSessionId, user.id(), orderId, result.paymentWebServiceAuthority(),
                order.getPaymentPrice(), currentDateTime
        );

        paymentSessionRepository.save(newSession);

        // create payment link to gateway
        // https://payment.zarinpal.com/pg/v4/payment/request.json
        // Location: https://payment.zarinpal.com/pg/StartPay/ . $result['data']["authority"]
    }

    public void requestPaymentVerification(
            ApplicationUser user,
            UUID paymentSessionId,
            String gatewayAuthority
    ) {
        PaymentSession paymentSession = paymentSessionRepository.find(paymentSessionId)
                .orElseThrow(() -> new PaymentSessionNotFoundException(paymentSessionId));

        if (!paymentSession.getUserId().equals(user.id())) {
            throw new PaymentSessionOwnerException(paymentSessionId, paymentSession.getUserId(), user.id());
        }

        if (!paymentSession.getState().equals(PaymentSessionState.CREATED)) {
            throw new BadSessionStatePaymentVerificationException(
                    paymentSession.getId(),
                    paymentSession.getState()
            );
        }

        if (!paymentSession.getPaymentWebServiceAuthority().equals(gatewayAuthority)) {
            throw new PaymentGatewayAuthorityMismatchException(
                    paymentSession.getPaymentWebServiceAuthority(),
                    gatewayAuthority
            );
        }

        var currentDateTime = LocalDateTime.now();

        //long secondsElapsed = Math.abs(Duration.between(currentDateTime, paymentSession.getCreatedAt()).toSeconds());
        //if (secondsElapsed <= 10) {
            // at least 30 second should elapse in order to create request verification
        //}

        Order order = orderRepository.find(paymentSession.getTargetOrderId())
                .orElseThrow(() -> new OrderNotFoundException(paymentSession.getTargetOrderId()));

        if (!order.getStatus().equals(OrderStatus.REGISTERED)) {
            throw new BadOrderStatusPaymentVerificationException(order.getStatus());
        }

        List<ProductForOrder> products = performProductCheckOnOrder(order);

        List<StockChange> changes = new ArrayList<>();

        for (ProductForOrder product : products) {
            OrderItem relatedItem = order.getItems().stream()
                    .filter(item -> item.getProductVariantId().equals(product.getId()))
                    .findFirst().orElseThrow();

            changes.add(StockChange.newChange(
                    uuidGenerator.generateNew(),
                    product.getId().productId(),
                    product.getId().variantIndex(),
                    user.id(),
                    false,
                    product.getStockItems(),
                    relatedItem.getCount(),
                    "خرید توسط مشتری " + user.fullName().value() + " / " + user.phoneNumber().value(),
                    currentDateTime
            ));

            product.newOrder(relatedItem.getCount(), currentDateTime);
        }

        paymentSession.markAsPaid(currentDateTime);
        order.markAsPaid(currentDateTime);

        eventPublisher.publish(new SessionMarkedAsPaidEvent(paymentSessionId));

        paymentSessionRepository.save(paymentSession);
        orderRepository.save(order);
        productRepository.saveAllForOrder(products);
        stockChangeRepository.saveAll(changes);
    }

    private final RetryTemplate retryTemplate = new RetryTemplate(RetryPolicy.builder()
            .maxRetries(3)
            .delay(Duration.ofSeconds(2))
            .multiplier(2)
            .build());

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Async
    @Retryable(excludes = PaymentSessionNotFoundException.class)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void tryVerifyPaymentSession(SessionMarkedAsPaidEvent event) {
        PaymentSession session = paymentSessionRepository.find(event.paymentSessionId())
                .orElseThrow(() -> new PaymentSessionNotFoundException(event.paymentSessionId()));

        if (!session.getState().equals(PaymentSessionState.PAID)) {
            return;
        }

        var currentDateTime = LocalDateTime.now();

        long secondsElapsed = Math.abs(Duration.between(currentDateTime, session.getPaidAt()).toSeconds());
        if (secondsElapsed > 3 * 60) {
            return;
        }

        retryTemplate.execute(() -> {
            restClient.post().uri("").body().retrieve().
        });

        session.markAsVerified();
        paymentSessionRepository.save(session);
    }

    // don't forget to lock the records and be aware of concurrency

    @Scheduled(fixedRate = 5, timeUnit = TimeUnit.SECONDS)
    public void handleNotVerifiedPaidPayments() {
        // find and lock
        // if after 2 minute they doesn't verified mark them and their order as failed
        // rollback they order and last_ordered_at
    }

    @Scheduled(fixedRate = 3, timeUnit = TimeUnit.SECONDS)
    public void finalizeVerifiedPayments() {
        // find and lock
        // find verified payments and set their order status to payment verified
    }

    // maybe we need a scheduled job for detecting and updating outdated orders(order clean up)
}
