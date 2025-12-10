package dev.hspl.hspl2shop.shop.value;

import org.jspecify.annotations.NullMarked;

@NullMarked
public record CategoryInfo(
        HumanReadableId id,
        CategoryName name
) {
}
