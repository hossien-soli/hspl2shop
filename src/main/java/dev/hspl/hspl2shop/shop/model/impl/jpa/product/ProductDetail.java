package dev.hspl.hspl2shop.shop.model.impl.jpa.product;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

@Entity(name = "ProductDetail")
@Table(name = "product_details")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class ProductDetail {
    @Id
    private String productId; // both primary key and foreign key to products

    @Column(name = "desc_ref")
    private String longDescriptionReference;

    private Set<String> extraImages; // Set<ImageReference>

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

}
