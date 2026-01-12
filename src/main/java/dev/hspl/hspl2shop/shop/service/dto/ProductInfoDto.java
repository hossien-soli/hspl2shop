package dev.hspl.hspl2shop.shop.service.dto;

import jakarta.validation.constraints.NotBlank;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

@NullMarked
public record ProductInfoDto(
        @NotBlank(message = "admin.product.id.required")
        String id,

        @NotBlank(message = "admin.product.category_id.required")
        String categoryId,

        @NotBlank(message = "admin.product.name.required")
        String name,

        @NotBlank(message = "admin.product.short_description.required")
        String shortDescription,

        @Nullable
        String longDescriptionReference,

        @Nullable
        String[] imageReferences,

        @Nullable
        Short sortingValue
) {
}
