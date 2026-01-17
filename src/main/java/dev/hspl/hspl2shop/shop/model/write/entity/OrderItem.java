package dev.hspl.hspl2shop.shop.model.write.entity;

import dev.hspl.hspl2shop.shop.value.ProductVariantId;
import lombok.Getter;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

@Getter
@NullMarked
public class OrderItem {
    private final ProductVariantId productVariantId;

    private final int variantPrice;

    @Nullable
    private final Short discountPercent; // variant discount percent

    private final short count;

    private OrderItem(
            ProductVariantId productVariantId, int variantPrice,
            @Nullable Short discountPercent, short count
    ) {
        this.productVariantId = productVariantId;
        this.variantPrice = variantPrice;
        this.discountPercent = discountPercent;
        this.count = count;
    }

    public static OrderItem createItem(
            ProductVariantId productVariantId, int variantPrice,
            @Nullable Short discountPercent, short count
    ) {
        return new OrderItem(productVariantId, variantPrice, discountPercent, count);
    }
}
