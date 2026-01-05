package dev.hspl.hspl2shop.shop.model.read.dto;

import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

@NullMarked
public record CategoryDetailDto(
        String id, // HumanReadableId

        String name,

        String shortDescription,

        @Nullable
        String longDescriptionReference,

        @Nullable
        String[] imageReferences
) {
}
