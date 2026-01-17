package dev.hspl.hspl2shop.shop.model.write.entity;

import dev.hspl.hspl2shop.shop.value.ProductName;
import dev.hspl.hspl2shop.shop.value.ProductVariantId;
import dev.hspl.hspl2shop.shop.value.VariantName;
import lombok.Getter;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.time.LocalDateTime;

// domain required product info for creating and handling orders for customers

@Getter
@NullMarked
public class ProductForOrder {
    private final ProductVariantId id;

    private final ProductName productName;

    private final VariantName variantName;

    private short stockItems;

    private final int price; // toman (without discount)

    @Nullable
    private final Short discountPercent; // 0-100

    private final int weight; // unit: g - 1000g

    private final boolean visible;

    @Nullable
    private LocalDateTime lastOrderedAt; // fill after order mark as paid

    @Nullable
    private final Short version;

    private ProductForOrder(
            ProductVariantId id, ProductName productName,
            VariantName variantName, short stockItems, int price, @Nullable Short discountPercent,
            int weight, boolean visible, @Nullable LocalDateTime lastOrderedAt, @Nullable Short version
    ) {
        this.id = id;
        this.productName = productName;
        this.variantName = variantName;
        this.stockItems = stockItems;
        this.price = price;
        this.discountPercent = discountPercent;
        this.weight = weight;
        this.visible = visible;
        this.lastOrderedAt = lastOrderedAt;
        this.version = version;
    }

    public static ProductForOrder existingProduct(
            ProductVariantId id, ProductName productName,
            VariantName variantName, short stockItems, int price, @Nullable Short discountPercent,
            int weight, boolean visible, @Nullable LocalDateTime lastOrderedAt, @Nullable Short version
    ) {
        return new ProductForOrder(id, productName, variantName, stockItems, price,
                discountPercent, weight, visible, lastOrderedAt, version);
    }

    public void newOrder(short orderItemCount, LocalDateTime currentDateTime) {
        this.stockItems = (short) (this.stockItems - orderItemCount);
        this.lastOrderedAt = currentDateTime;
    }
}
