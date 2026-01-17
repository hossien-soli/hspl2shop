package dev.hspl.hspl2shop.shop.model.impl.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

@Entity(name = "DeliveryMethod")
@Table(name = "delivery_methods")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@NullMarked
public class DeliveryMethodJpaEntity {
    @Id
    @Column(name = "id")
    private short id; // not auto generated

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private int basePrice; // toman

    @Column(name = "discount")
    @Nullable
    private Short discountPercent; // 0-100

    @Column(name = "max_weight_kg")
    private short maxWeightPossibleKG;

    @Column(name = "active")
    private boolean active;
}
