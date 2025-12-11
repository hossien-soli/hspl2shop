package dev.hspl.hspl2shop.shop.model.query.dto;

import dev.hspl.hspl2shop.common.value.FullName;
import dev.hspl.hspl2shop.common.value.PhoneNumber;
import dev.hspl.hspl2shop.shop.value.LiteralFullAddress;
import dev.hspl.hspl2shop.shop.value.MapLocation;
import dev.hspl.hspl2shop.shop.value.PostalCode;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.time.LocalDateTime;
import java.util.UUID;

@NullMarked
public record DetailedUserAddress(
        UUID id,
        FullName deliveryFullName,
        PhoneNumber deliveryPhoneNumber,
        @Nullable PhoneNumber secondaryPhoneNumber,
        short provinceId,
        short cityId,
        String provinceName,
        String cityName,
        LiteralFullAddress literalFullAddress,
        PostalCode postalCode,
        @Nullable MapLocation mapLocation,
        LocalDateTime createdAt
) {
}
