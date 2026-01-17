package dev.hspl.hspl2shop.common.model;

import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.time.LocalDateTime;
import java.util.UUID;

@NullMarked
public record UserAddressDto(
        UUID id,
        String deliveryFullName,
        String deliveryPhoneNumber,
        @Nullable String secondaryPhoneNumber,
        short provinceId,
        short cityId,
        String provinceName,
        String cityName,
        String literalFullAddress,
        String postalCode,
        @Nullable Double locationLat,
        @Nullable Double locationLong,
        LocalDateTime createdAt,
        @Nullable LocalDateTime updatedAt,
        short version
) {
}
