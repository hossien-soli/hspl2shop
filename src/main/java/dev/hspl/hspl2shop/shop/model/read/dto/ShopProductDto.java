package dev.hspl.hspl2shop.shop.model.read.dto;

import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

// product representation for customers and for the shop ui

@NullMarked
public record ShopProductDto(
        String id, // HumanReadableId

        String categoryId,
        String categoryName,

        String name,
        String shortDescription,

        @Nullable
        String longDescriptionReference,

        @Nullable
        String[] imageReferences,

        boolean discounted
) {
}
