package dev.hspl.hspl2shop.user.model.impl.jpa.entity;

import jakarta.persistence.*;
import lombok.*;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity(name = "UserAddress")
@Table(name = "user_addresses")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@NullMarked
public class UserAddressJpaEntity {
    @Id
    @Column(name = "id")
    private UUID id;

    @Column(name = "user_id")
    private UUID userId;

    @Column(name = "name")
    private String deliveryFullName;

    @Column(name = "phone1")
    private String deliveryPhoneNumber;

    @Column(name = "phone2")
    @Nullable
    private String secondaryPhoneNumber;

//    @ManyToOne(fetch = FetchType.LAZY, optional = false)
//    @JoinColumn(name = "province_id")
//    private Province province;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "city_id")
    private City city;

    @Column(name = "address")
    private String literalFullAddress;

    @Column(name = "postal_code")
    private String postalCode;

    @Column(name = "llat")
    @Nullable
    private Double locationLat;

    @Column(name = "llong")
    @Nullable
    private Double locationLong;

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
