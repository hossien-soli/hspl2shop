package dev.hspl.hspl2shop.shop.model.impl.jpa.entity;

import jakarta.persistence.*;
import lombok.*;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

@Entity(name = "OrderItem")
@Table(name = "order_items")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@NullMarked
public class OrderItemJpaEntity {
    @EmbeddedId
    private OrderItemId id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId("orderId")
    @JoinColumn(name = "order_id")
    private OrderJpaEntity order;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumns({
            @JoinColumn(name = "product_id", referencedColumnName = "product_id"),
            @JoinColumn(name = "variant_index", referencedColumnName = "index")
    })
    private VariantJpaEntity productVariant;

    @Column(name = "variant_price")
    private int variantPrice;

    @Column(name = "variant_discount")
    @Nullable
    private Short discountPercent; // variant discount percent

    @Column(name = "count")
    private short count;
}
