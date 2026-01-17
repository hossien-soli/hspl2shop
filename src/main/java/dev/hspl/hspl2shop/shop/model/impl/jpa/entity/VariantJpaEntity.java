package dev.hspl.hspl2shop.shop.model.impl.jpa.entity;

import jakarta.persistence.*;
import lombok.*;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.time.LocalDateTime;

// ProductVariant

@Entity(name = "Variant")
@Table(name = "product_variants")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@NullMarked
public class VariantJpaEntity {
    @EmbeddedId
    private VariantId id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId("productId")
    @JoinColumn(name = "product_id")
    private ProductJpaEntity product;

    @Column(name = "product_name")
    private String productName; // denormalizes from products table for better performance

    @Column(name = "variant_name")
    private String variantName;

    @Column(name = "stock")
    private short stockItems;

    @Column(name = "price")
    private int price; // toman

    @Column(name = "discount")
    @Nullable
    private Short discountPercent; // 0-100

    @Column(name = "weight")
    private int weight; // unit: g - 1000g

    @Column(name = "visible")
    private boolean visible;

    @Column(name = "ordered_at")
    @Nullable
    private LocalDateTime lastOrderedAt;
    
    @Column(name = "version")
    @Version
    @Nullable
    private Short version;
}
