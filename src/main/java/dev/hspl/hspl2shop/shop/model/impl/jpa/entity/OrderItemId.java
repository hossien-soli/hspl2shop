package dev.hspl.hspl2shop.shop.model.impl.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@EqualsAndHashCode
public class OrderItemId implements Serializable {
    @Column(name = "order_id")
    private UUID orderId;

    @Column(name = "product_id")
    private String productId;

    @Column(name = "variant_index")
    private short variantIndex;
}
