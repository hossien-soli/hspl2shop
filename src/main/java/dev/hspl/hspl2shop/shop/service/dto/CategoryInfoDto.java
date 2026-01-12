package dev.hspl.hspl2shop.shop.service.dto;

import jakarta.validation.constraints.NotBlank;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

@NullMarked
public record CategoryInfoDto(
        @NotBlank(message = "admin.category.id.required")
        String id, // human-readable-id

        @NotBlank(message = "admin.category.name.required")
        String name,

        @NotBlank(message = "admin.category.short_description.required")
        String shortDescription,

        @Nullable
        String longDescriptionReference,

        @Nullable
        String[] imageReferences,

        @Nullable
        Short sortingValue
) {
}
