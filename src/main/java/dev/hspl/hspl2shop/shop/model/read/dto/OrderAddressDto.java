package dev.hspl.hspl2shop.shop.model.read.dto;

import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.UUID;

@NullMarked
public record OrderAddressDto(
        UUID id,

        String deliveryFullName,

        String deliveryPhoneNumber,

        @Nullable
        String secondaryPhoneNumber,

        short provinceId,
        String provinceName,

        short cityId,
        String cityName,

        String literalFullAddress,

        String postalCode,

        @Nullable
        Double locationLat,

        @Nullable
        Double locationLong,

        short version
) {
}
