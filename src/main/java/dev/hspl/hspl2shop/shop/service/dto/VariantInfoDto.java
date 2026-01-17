package dev.hspl.hspl2shop.shop.service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

@NullMarked
public record VariantInfoDto(
        @NotNull(message = "admin.product.variant.index.required")
        Short variantIndex,

        @NotBlank(message = "admin.product.variant.name.required")
        String variantName,

        @NotNull(message = "admin.product.variant.price.required")
        Integer variantPrice,

        @Nullable
        Short discountPercent,

        @NotNull(message = "admin.product.variant.weight.required")
        Integer variantWeight
) {
}
