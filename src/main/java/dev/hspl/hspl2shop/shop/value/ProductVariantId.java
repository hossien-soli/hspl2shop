package dev.hspl.hspl2shop.shop.value;

import org.jspecify.annotations.NullMarked;

@NullMarked
public record ProductVariantId(
        HumanReadableId productId,
        short variantIndex
) {
}
