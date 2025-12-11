package dev.hspl.hspl2shop.shop.model.impl.jpa.product;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@EqualsAndHashCode
public class ProductVariantId implements Serializable {
    @Column(name = "product_id")
    private String productId;

    @Column(name = "index")
    private short variantIndex;
}
