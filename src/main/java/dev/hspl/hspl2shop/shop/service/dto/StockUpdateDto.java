package dev.hspl.hspl2shop.shop.service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.jspecify.annotations.NullMarked;

@NullMarked
public record StockUpdateDto(
        @NotNull(message = "admin.product.variant.stock_update.invalid_data")
        Boolean isIncrease,

        @NotNull(message = "admin.product.variant.stock_update.invalid_data")
        Short count,

        @NotBlank(message = "admin.product.variant.stock_update.invalid_data")
        String description
) {
}
