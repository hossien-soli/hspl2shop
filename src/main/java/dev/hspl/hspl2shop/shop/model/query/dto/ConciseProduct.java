package dev.hspl.hspl2shop.shop.model.query.dto;

import dev.hspl.hspl2shop.shop.value.HumanReadableId;
import dev.hspl.hspl2shop.shop.value.ImageReference;
import dev.hspl.hspl2shop.shop.value.ProductName;
import dev.hspl.hspl2shop.shop.value.ShortDescription;
import org.jspecify.annotations.NullMarked;

@NullMarked
public record ConciseProduct(
        HumanReadableId id,
        ProductName name,
        ImageReference primaryImage,
        ShortDescription shortDescription,
        boolean discountFlag
) {
}
