package dev.hspl.hspl2shop.shop.model.impl.jpa.entity;

import jakarta.persistence.*;
import lombok.*;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.UUID;

@Entity(name = "OrderAddress")
@Table(name = "order_addresses")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@NullMarked
public class OrderAddressJpaEntity {
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

//    @Column(name = "province_id")
//    private short provinceId;

    @Column(name = "province_name")
    private String provinceName;

//    @Column(name = "city_id")
//    private short cityId;

    @Column(name = "city_name")
    private String cityName;

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

    @Column(name = "version")
    @Version
    @Nullable
    private Short version;
}
