package dev.hspl.hspl2shop.shop.model.impl.jpa.entity;

import dev.hspl.hspl2shop.shop.value.OrderStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity(name = "Order")
@Table(name = "orders")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@NullMarked
public class OrderJpaEntity {
    @Id
    @Column(name = "id")
    private UUID id;

    @Column(name = "user_id")
    @Nullable // can be null only if user deleted from system
    private UUID userId;

    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private List<OrderItemJpaEntity> items;

    @Column(name = "payment_price")
    private long paymentPrice;

    @Column(name = "total_discount")
    @Nullable
    private Integer totalDiscount;

    @Column(name = "status", columnDefinition = "ORDER_STATUS")
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Column(name = "delivery_method_id")
    private short deliveryMethodId;

    @Column(name = "delivery_method_name")
    private String deliveryMethodName;

    @Column(name = "delivery_base_price")
    private int deliveryBasePrice;

    @Column(name = "delivery_discount_percent")
    @Nullable
    private Short deliveryDiscountPercent;

    @Column(name = "address_id")
    private UUID orderAddressId;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "paid_at")
    @Nullable
    private LocalDateTime paidAt;

    @Column(name = "verified_at")
    @Nullable
    private LocalDateTime paymentVerifiedAt;

    @Column(name = "failed_at")
    @Nullable
    private LocalDateTime paymentFailedAt;

    @Column(name = "received_at")
    @Nullable
    private LocalDateTime receivedAt;

    @Column(name = "delivered_at")
    @Nullable
    private LocalDateTime deliveredAt;

    @Column(name = "cancelled_at")
    @Nullable
    private LocalDateTime cancelledAt;

    @Column(name = "outdated_at")
    @Nullable
    private LocalDateTime outdatedAt;

    @Column(name = "version")
    @Version
    @Nullable
    private Short version;
}
