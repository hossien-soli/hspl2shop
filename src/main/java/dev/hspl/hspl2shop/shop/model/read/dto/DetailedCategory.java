package dev.hspl.hspl2shop.shop.model.read.dto;

import dev.hspl.hspl2shop.shop.value.HumanReadableId;
import dev.hspl.hspl2shop.shop.value.ImageReference;
import dev.hspl.hspl2shop.shop.value.ShortDescription;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.Set;

@NullMarked
public record DetailedCategory(
        HumanReadableId id,

        String name,

        ShortDescription shortDescription,

//        @Nullable
//    LongDescriptionReference longDescription, // detailed description

        @Nullable
    ImageReference primaryImage,

        @Nullable
    Set<ImageReference> extraImages,

        @Nullable
    Short sortingValue
) {
}
