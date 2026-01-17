package dev.hspl.hspl2shop.shop.model.write.entity;

import dev.hspl.hspl2shop.shop.value.OrderDeliveryInfo;
import dev.hspl.hspl2shop.shop.value.OrderStatus;
import lombok.Getter;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

// and we notice the user that we failed to process the payment
// after first payment verificiation try we should only see this message to user: your order receivevd
// like digikala and we shouldn't directly tell him that everything is successful!!

// TODO: set lifetime on orders and user can hold them up to 1 hours(because of price changes)
// سفارشات رو زمان کوتاه تری نگه دار جهت کاهش چک کردن موجودی محصول برای پرداخت
// این سفارش تا فلان ساعت دیگه لغو خواهد شد
// تغییر روش ارسال
// ایتم های سفارش بعد از ایجاد قابل تغییر نیستند!

@Getter
@NullMarked
public class Order {
    private final UUID id;

    @Nullable
    private final UUID userId; // can be null only if user deleted from system

    private final List<OrderItem> items; // TODO: apply limit on items

    private final long paymentPrice; // final order price for payment / toman

    @Nullable
    private final Integer totalDiscount; // toman

    private OrderStatus status;

    private final OrderDeliveryInfo deliveryInfo;

    private UUID orderAddressId;

    private final LocalDateTime createdAt;

    @Nullable
    private LocalDateTime paidAt;

    @Nullable
    private LocalDateTime paymentVerifiedAt;

    @Nullable
    private LocalDateTime paymentFailedAt;

    @Nullable
    private LocalDateTime receivedAt;

    @Nullable
    private LocalDateTime deliveredAt;

    @Nullable
    private LocalDateTime cancelledAt;

    @Nullable
    private LocalDateTime outdatedAt;

    @Nullable
    private final Short version;

    private Order(
            UUID id, @Nullable UUID userId, List<OrderItem> items, long paymentPrice,
            @Nullable Integer totalDiscount,
            OrderStatus status, OrderDeliveryInfo deliveryInfo, UUID orderAddressId,
            LocalDateTime createdAt, @Nullable LocalDateTime paidAt,
            @Nullable LocalDateTime paymentVerifiedAt, @Nullable LocalDateTime paymentFailedAt,
            @Nullable LocalDateTime receivedAt, @Nullable LocalDateTime deliveredAt,
            @Nullable LocalDateTime cancelledAt, @Nullable LocalDateTime outdatedAt,
            @Nullable Short version
    ) {
        // TODO: add validation for items should not be empty

        this.id = id;
        this.userId = userId;
        this.items = items;
        this.paymentPrice = paymentPrice;
        this.totalDiscount = totalDiscount;
        this.status = status;
        this.deliveryInfo = deliveryInfo;
        this.orderAddressId = orderAddressId;
        this.createdAt = createdAt;
        this.paidAt = paidAt;
        this.paymentVerifiedAt = paymentVerifiedAt;
        this.paymentFailedAt = paymentFailedAt;
        this.receivedAt = receivedAt;
        this.deliveredAt = deliveredAt;
        this.cancelledAt = cancelledAt;
        this.outdatedAt = outdatedAt;
        this.version = version;
    }

    public static Order newOrder(
            UUID newOrderId, UUID userId, List<OrderItem> items, long paymentPrice,
            @Nullable Integer totalDiscount,
            OrderDeliveryInfo deliveryInfo, UUID orderAddressId, LocalDateTime currentDateTime
    ) {
        return new Order(newOrderId, userId, items, paymentPrice, totalDiscount, OrderStatus.REGISTERED,
                deliveryInfo, orderAddressId, currentDateTime, null, null, null, null, null, null, null, null);
    }

    public static Order existingOrder(
            UUID id, @Nullable UUID userId, List<OrderItem> items, long paymentPrice,
            @Nullable Integer totalDiscount,
            OrderStatus status, OrderDeliveryInfo deliveryInfo, UUID orderAddressId,
            LocalDateTime createdAt, @Nullable LocalDateTime paidAt,
            @Nullable LocalDateTime paymentVerifiedAt, @Nullable LocalDateTime paymentFailedAt,
            @Nullable LocalDateTime receivedAt, @Nullable LocalDateTime deliveredAt,
            @Nullable LocalDateTime cancelledAt, @Nullable LocalDateTime outdatedAt,
            @Nullable Short version
    ) {
        return new Order(id, userId, items, paymentPrice, totalDiscount, status, deliveryInfo,
                orderAddressId, createdAt, paidAt, paymentVerifiedAt, paymentFailedAt, receivedAt,
                deliveredAt, cancelledAt, outdatedAt, version);
    }

    public void changeOrderAddress(UUID newOrderAddressId) {
        this.orderAddressId = newOrderAddressId; // old order_address record should be deleted
    }

    public void markAsPaid(LocalDateTime currentDateTime) {
        this.status = OrderStatus.PAID;
        this.paidAt = currentDateTime;
    }
}
