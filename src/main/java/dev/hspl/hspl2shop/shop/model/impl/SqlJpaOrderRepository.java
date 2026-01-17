package dev.hspl.hspl2shop.shop.model.impl;

import dev.hspl.hspl2shop.common.exception.EntityVersionMismatchException;
import dev.hspl.hspl2shop.shop.model.impl.jpa.entity.OrderItemId;
import dev.hspl.hspl2shop.shop.model.impl.jpa.entity.OrderItemJpaEntity;
import dev.hspl.hspl2shop.shop.model.impl.jpa.entity.OrderJpaEntity;
import dev.hspl.hspl2shop.shop.model.impl.jpa.entity.VariantId;
import dev.hspl.hspl2shop.shop.model.impl.jpa.repository.OrderJpaRepository;
import dev.hspl.hspl2shop.shop.model.impl.jpa.repository.VariantJpaRepository;
import dev.hspl.hspl2shop.shop.model.write.entity.Order;
import dev.hspl.hspl2shop.shop.model.write.entity.OrderItem;
import dev.hspl.hspl2shop.shop.model.write.repository.OrderRepository;
import dev.hspl.hspl2shop.shop.value.DeliveryMethodName;
import dev.hspl.hspl2shop.shop.value.HumanReadableId;
import dev.hspl.hspl2shop.shop.value.OrderDeliveryInfo;
import dev.hspl.hspl2shop.shop.value.ProductVariantId;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NullMarked;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
@NullMarked
public class SqlJpaOrderRepository implements OrderRepository {
    private final OrderJpaRepository jpaRepository;
    private final VariantJpaRepository variantJpaRepository;

    @Override
    public Optional<Order> find(UUID id) {
        return jpaRepository.findByIdWithItems(id).map(jpaEntity -> {
            List<OrderItem> items = jpaEntity.getItems().stream()
                    .map(itemJpaEntity -> OrderItem.createItem(
                            new ProductVariantId(
                                    new HumanReadableId(itemJpaEntity.getId().getProductId()),
                                    itemJpaEntity.getId().getVariantIndex()
                            ),
                            itemJpaEntity.getVariantPrice(),
                            itemJpaEntity.getDiscountPercent(),
                            itemJpaEntity.getCount()
                    )).toList();

            return Order.existingOrder(
                    jpaEntity.getId(), jpaEntity.getUserId(), items, jpaEntity.getPaymentPrice(),
                    jpaEntity.getTotalDiscount(), jpaEntity.getStatus(),
                    new OrderDeliveryInfo(
                            jpaEntity.getDeliveryMethodId(),
                            new DeliveryMethodName(jpaEntity.getDeliveryMethodName()),
                            jpaEntity.getDeliveryBasePrice(),
                            jpaEntity.getDeliveryDiscountPercent()
                    ),
                    jpaEntity.getOrderAddressId(), jpaEntity.getCreatedAt(), jpaEntity.getPaidAt(),
                    jpaEntity.getPaymentVerifiedAt(), jpaEntity.getPaymentFailedAt(),
                    jpaEntity.getReceivedAt(), jpaEntity.getDeliveredAt(), jpaEntity.getCancelledAt(),
                    jpaEntity.getOutdatedAt(), jpaEntity.getVersion()
            );
        });
    }

    @Override
    public void save(Order order) throws EntityVersionMismatchException {
        try {
            List<OrderItemJpaEntity> items = order.getItems().stream()
                    .map(item -> OrderItemJpaEntity.builder()
                            .id(OrderItemId.builder().orderId(order.getId())
                                    .productId(item.getProductVariantId().productId().value())
                                    .variantIndex(item.getProductVariantId().variantIndex()).build())
                            .order(jpaRepository.getReferenceById(order.getId()))
                            .productVariant(variantJpaRepository.getReferenceById(
                                    VariantId.builder()
                                            .productId(item.getProductVariantId().productId().value())
                                            .variantIndex(item.getProductVariantId().variantIndex()).build()
                            ))
                            .variantPrice(item.getVariantPrice())
                            .discountPercent(item.getDiscountPercent())
                            .count(item.getCount())
                            .build()).toList();

            jpaRepository.saveAndFlush(OrderJpaEntity.builder()
                    .id(order.getId())
                    .userId(order.getUserId())
                    .items(items)
                    .paymentPrice(order.getPaymentPrice())
                    .totalDiscount(order.getTotalDiscount())
                    .status(order.getStatus())
                    .deliveryMethodId(order.getDeliveryInfo().methodId())
                    .deliveryMethodName(order.getDeliveryInfo().methodName().value())
                    .deliveryBasePrice(order.getDeliveryInfo().basePrice())
                    .deliveryDiscountPercent(order.getDeliveryInfo().discountPercent())
                    .orderAddressId(order.getOrderAddressId())
                    .createdAt(order.getCreatedAt())
                    .paidAt(order.getPaidAt())
                    .paymentVerifiedAt(order.getPaymentVerifiedAt())
                    .paymentFailedAt(order.getPaymentFailedAt())
                    .receivedAt(order.getReceivedAt())
                    .deliveredAt(order.getDeliveredAt())
                    .cancelledAt(order.getCancelledAt())
                    .outdatedAt(order.getOutdatedAt())
                    .version(order.getVersion())
                    .build());
        } catch (OptimisticLockingFailureException exception) {
            throw new EntityVersionMismatchException(Order.class.getSimpleName(), order.getId().toString());
        }
    }
}
