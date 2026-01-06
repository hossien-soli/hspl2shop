package dev.hspl.hspl2shop.shop.model.read.dto;

import org.jspecify.annotations.NullMarked;

// category representation for customers and for the shop ui

@NullMarked
public record ShopCategoryDto(
        String id, // HumanReadableId
        String name
) {
}
