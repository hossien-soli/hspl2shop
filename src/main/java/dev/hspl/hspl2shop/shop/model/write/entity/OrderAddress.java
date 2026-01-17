package dev.hspl.hspl2shop.shop.model.write.entity;

import dev.hspl.hspl2shop.common.value.*;
import lombok.Getter;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.UUID;

@Getter
@NullMarked
public class OrderAddress {
    private final UUID id;

    private final UUID userId;

    private FullName deliveryFullName; // delivery fullName(default=user's fullName)

    private PhoneNumber deliveryPhoneNumber; // delivery phoneNumber(default=user's phoneNumber)

    @Nullable
    private PhoneNumber secondaryPhoneNumber; // optional

    //private short provinceId;

    private String provinceName;

    //private short cityId;

    private String cityName;

    private LiteralFullAddress literalFullAddress;

    private PostalCode postalCode;

    @Nullable
    private MapLocation mapLocation;

    @Nullable
    private final Short version;

    private OrderAddress(
            UUID id, UUID userId, FullName deliveryFullName, PhoneNumber deliveryPhoneNumber,
            @Nullable PhoneNumber secondaryPhoneNumber, String provinceName,
            String cityName, LiteralFullAddress literalFullAddress, PostalCode postalCode,
            @Nullable MapLocation mapLocation, @Nullable Short version
    ) {
        this.id = id;
        this.userId = userId;
        this.deliveryFullName = deliveryFullName;
        this.deliveryPhoneNumber = deliveryPhoneNumber;
        this.secondaryPhoneNumber = secondaryPhoneNumber;
        //this.provinceId = provinceId;
        this.provinceName = provinceName;
        //this.cityId = cityId;
        this.cityName = cityName;
        this.literalFullAddress = literalFullAddress;
        this.postalCode = postalCode;
        this.mapLocation = mapLocation;
        this.version = version;
    }

    public static OrderAddress newAddress(
            UUID newAddressId, UUID userId, FullName deliveryFullName, PhoneNumber deliveryPhoneNumber,
            @Nullable PhoneNumber secondaryPhoneNumber, String provinceName,
            String cityName, LiteralFullAddress literalFullAddress, PostalCode postalCode,
            @Nullable MapLocation mapLocation
    ) {
        return new OrderAddress(newAddressId, userId, deliveryFullName, deliveryPhoneNumber,
                secondaryPhoneNumber, provinceName, cityName,
                literalFullAddress, postalCode, mapLocation, null);
    }
}
