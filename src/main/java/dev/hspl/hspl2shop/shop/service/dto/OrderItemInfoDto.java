package dev.hspl.hspl2shop.shop.service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.jspecify.annotations.NullMarked;

@NullMarked
public record OrderItemInfoDto(
        @NotBlank(message = "order_registration.items.product_id.required")
        String productId,

        @NotNull(message = "order_registration.items.variant_index.required")
        Short variantIndex,

        @NotNull(message = "order_registration.items.count.required")
        Short count
) {
}
