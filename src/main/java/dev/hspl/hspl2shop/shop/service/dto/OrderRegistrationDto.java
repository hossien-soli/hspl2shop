package dev.hspl.hspl2shop.shop.service.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.jspecify.annotations.NullMarked;

import java.util.List;
import java.util.UUID;

@NullMarked
public record OrderRegistrationDto(
        @Valid
        List<OrderItemInfoDto> items,

        @NotNull(message = "order_registration.delivery_method_id.required")
        Short deliveryMethodId,

        @NotNull(message = "order_registration.user_address_id.required")
        UUID userAddressId
) {
}
