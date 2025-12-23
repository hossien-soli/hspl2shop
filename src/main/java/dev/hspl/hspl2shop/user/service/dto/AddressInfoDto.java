package dev.hspl.hspl2shop.user.service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

@NullMarked
public record AddressInfoDto(
        @NotBlank(message = "user.address.delivery_full_name.required")
        String deliveryFullName,

        @NotBlank(message = "user.address.delivery_phone_number.required")
        String deliveryPhoneNumber,

        @Nullable
        String secondaryPhoneNumber,

        @NotNull(message = "user.address.city_id.required")
        Short cityId,

        @NotBlank(message = "user.address.literal_full_address.required")
        String literalFullAddress,

        @NotBlank(message = "user.address.postal_code.required")
        String postalCode,

        @Nullable
        Double locationLat,

        @Nullable
        Double locationLong
) {
}
