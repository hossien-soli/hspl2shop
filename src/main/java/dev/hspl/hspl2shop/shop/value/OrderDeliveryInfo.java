package dev.hspl.hspl2shop.shop.value;

import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

@NullMarked
public record OrderDeliveryInfo(
        short methodId,

        DeliveryMethodName methodName, // dmName should be denormalized on order entity(doesn't have to be in sync with actual records)

        int basePrice, // toman

        @Nullable
        Short discountPercent

        // maybe we need another field here for order specific delivery price computation
) {
    public OrderDeliveryInfo {
        // TODO: add validation(only methodId cannot be zero) throw generic validation exception
    }
}
