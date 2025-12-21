package dev.hspl.hspl2shop.user.model.write.entity;

import dev.hspl.hspl2shop.common.value.*;
import lombok.Getter;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@NullMarked
public class UserAddress {
    private final UUID id;

    private final UUID userId;

    private FullName deliveryFullName; // delivery fullName(default=user's fullName)

    private PhoneNumber deliveryPhoneNumber; // delivery phoneNumber(default=user's phoneNumber)

    @Nullable
    private PhoneNumber secondaryPhoneNumber; // optional

    private short cityId;

    private LiteralFullAddress literalFullAddress;

    private PostalCode postalCode;

    @Nullable
    private MapLocation mapLocation;

    private final LocalDateTime createdAt;

    @Nullable
    private LocalDateTime updatedAt;

    @Nullable
    private final Short version; // this field should be managed by Repository implementations
    // in domain or business logic only use it for client-side concurrency control checks
    // null = entity is new and should be persisted

    private UserAddress(
            UUID id, UUID userId, FullName deliveryFullName, PhoneNumber deliveryPhoneNumber,
            @Nullable PhoneNumber secondaryPhoneNumber, short cityId,
            LiteralFullAddress literalFullAddress, PostalCode postalCode,
            @Nullable MapLocation mapLocation, LocalDateTime createdAt,
            @Nullable LocalDateTime updatedAt, @Nullable Short version
    ) {
        this.id = id;
        this.userId = userId;
        this.deliveryFullName = deliveryFullName;
        this.deliveryPhoneNumber = deliveryPhoneNumber;
        this.secondaryPhoneNumber = secondaryPhoneNumber;
        this.cityId = cityId;
        this.literalFullAddress = literalFullAddress;
        this.postalCode = postalCode;
        this.mapLocation = mapLocation;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.version = version;
    }

    public static UserAddress newAddress(
            UUID newId, UUID userId, FullName deliveryFullName, PhoneNumber deliveryPhoneNumber,
            @Nullable PhoneNumber secondaryPhoneNumber, short cityId, LiteralFullAddress literalFullAddress,
            PostalCode postalCode, @Nullable MapLocation mapLocation, LocalDateTime currentDateTime
    ) {
        return new UserAddress(newId, userId, deliveryFullName, deliveryPhoneNumber, secondaryPhoneNumber,
                cityId, literalFullAddress, postalCode, mapLocation, currentDateTime, null, null);
    }

    public static UserAddress existingAddress(
            UUID id, UUID userId, FullName deliveryFullName, PhoneNumber deliveryPhoneNumber,
            @Nullable PhoneNumber secondaryPhoneNumber, short cityId,
            LiteralFullAddress literalFullAddress, PostalCode postalCode,
            @Nullable MapLocation mapLocation, LocalDateTime createdAt,
            @Nullable LocalDateTime updatedAt, @Nullable Short version
    ) {
        return new UserAddress(id, userId, deliveryFullName, deliveryPhoneNumber, secondaryPhoneNumber,
                cityId, literalFullAddress, postalCode, mapLocation, createdAt, updatedAt, version);
    }

    public void editAddress(
            FullName deliveryFullName, PhoneNumber deliveryPhoneNumber,
            @Nullable PhoneNumber secondaryPhoneNumber, short cityId,
            LiteralFullAddress literalFullAddress, PostalCode postalCode,
            @Nullable MapLocation mapLocation, LocalDateTime currentDateTime
    ) {
        this.deliveryFullName = deliveryFullName;
        this.deliveryPhoneNumber = deliveryPhoneNumber;
        this.secondaryPhoneNumber = secondaryPhoneNumber;
        this.cityId = cityId;
        this.literalFullAddress = literalFullAddress;
        this.postalCode = postalCode;
        this.mapLocation = mapLocation;
        this.updatedAt = currentDateTime;
    }
}
