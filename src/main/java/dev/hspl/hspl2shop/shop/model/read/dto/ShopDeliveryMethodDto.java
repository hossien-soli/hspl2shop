package dev.hspl.hspl2shop.shop.model.read.dto;

import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

// only active delivery methods for showing to customers to select

@NullMarked
public record ShopDeliveryMethodDto(
        short id,

        String name,

        @Nullable
        String description,

        @Nullable
        String iconImageReference,

        int basePrice,

        @Nullable
        Short discountPercent
) {
}
