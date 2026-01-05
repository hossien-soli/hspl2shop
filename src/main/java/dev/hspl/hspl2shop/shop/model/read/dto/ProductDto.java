package dev.hspl.hspl2shop.shop.model.read.dto;

import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

@NullMarked
public record ProductDto(
        String id, // HumanReadableId

        String categoryId,
        String categoryName,

        String name,
        String shortDescription,

        @Nullable
        String longDescriptionReference,

        @Nullable
        String[] imageReferences,

        boolean discountFlag
) {
}
