package dev.hspl.hspl2shop.shop.model.read.dto;

import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

// variant representation for customers and for the shop ui

@NullMarked
public record ShopVariantDto(
        short variantIndex,

        String variantName,

        short stockItems,

        int price,

        @Nullable
        Short discountPercent
) {
}
