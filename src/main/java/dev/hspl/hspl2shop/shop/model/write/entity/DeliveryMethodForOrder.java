package dev.hspl.hspl2shop.shop.model.write.entity;

import dev.hspl.hspl2shop.shop.value.DeliveryMethodName;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

// read only entity

@NullMarked
public record DeliveryMethodForOrder(
        short id,

        DeliveryMethodName name,

        int basePrice,

        @Nullable
        Short discountPercent,

        short maxWeightPossibleKG,

        boolean active
) {
}
