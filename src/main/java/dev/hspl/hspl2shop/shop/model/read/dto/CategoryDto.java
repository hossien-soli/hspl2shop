package dev.hspl.hspl2shop.shop.model.read.dto;

import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.time.LocalDateTime;

// CategoryDto for admin query service

@NullMarked
public record CategoryDto(
        String id,

        String name,

        String shortDescription,

        @Nullable
        String longDescriptionReference,

        @Nullable
        String[] imageReferences,

        @Nullable
        Short sortingValue,

        LocalDateTime createdAt,

        @Nullable
        LocalDateTime updatedAt,

        short version // used for client side concurrency control
) {
}
