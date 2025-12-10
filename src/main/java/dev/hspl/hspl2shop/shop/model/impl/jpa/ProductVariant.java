package dev.hspl.hspl2shop.shop.model.impl.jpa;

import jakarta.persistence.*;
import lombok.*;

@Entity(name = "ProductVariant")
@Table(name = "product_variants")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class ProductVariant {
    @EmbeddedId
    private ProductVariantId id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId("productId")
    private Product product;

    @Column(name = "product_name")
    private String productName; // denormalizes from products table for better performance

    @Column(name = "variant_name")
    private String variantName;

    @Column(name = "sort")
    private Short sortingValue;
}
