package dev.hspl.hspl2shop.shop.model.impl.jpa.entity;

import jakarta.persistence.*;
import lombok.*;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

@Entity(name = "ProductVariant")
@Table(name = "product_variants")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@NullMarked
public class ProductVariant {
    @EmbeddedId
    private ProductVariantId id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId("productId")
    private Product product;

//    @Column(name = "product_name")
//    private String productName; // denormalizes from products table for better performance

    @Column(name = "variant_name")
    private String variantName;

    @Column(name = "stock")
    private int stockItems;

    @Column(name = "price")
    private int price;

    @Column(name = "discount")
    @Nullable
    private Byte discountPercent;

    @Column(name = "visible")
    private boolean visible;

    @Column(name = "version")
    @Version
    @Nullable
    private Short version;
}
