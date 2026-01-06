package dev.hspl.hspl2shop.shop.model.read.dto;

import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

// category-detail representation for customers and for the shop ui

@NullMarked
public record ShopCategoryDetailDto(
        String id, // HumanReadableId

        String name,

        String shortDescription,

        @Nullable
        String longDescriptionReference,

        @Nullable
        String[] imageReferences
) {
}
