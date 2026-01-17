package dev.hspl.hspl2shop.shop.model.impl.jpa.entity;

import jakarta.persistence.*;
import lombok.*;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.time.LocalDateTime;

@Entity(name = "DeliveryMethodDetail")
@Table(name = "delivery_method_details")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@NullMarked
public class DeliveryMethodDetail {
    @Id
    @Column(name = "id")
    private short id; // not auto generated / shared PRIMARY KEY with DeliveryMethodJpaEntity

    @OneToOne(fetch = FetchType.LAZY, optional = false, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    @MapsId
    @JoinColumn(name = "id")
    private DeliveryMethodJpaEntity deliveryMethod;

    @Column(name = "description")
    @Nullable
    private String description; // varchar(255)

    @Column(name = "icon")
    @Nullable
    private String iconImageReference;

    @Column(name = "sort")
    @Nullable
    private Short sortingValue;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @Nullable
    private LocalDateTime updatedAt;

    @Column(name = "version")
    @Version
    @Nullable
    private Short version;
}
