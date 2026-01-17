package dev.hspl.hspl2shop.shop.model.write.entity;

import dev.hspl.hspl2shop.shop.value.VariantName;
import lombok.Getter;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.time.LocalDateTime;

// ProductVariant
// this entity can only be managed by OWNER
// administration representation of Variant entity in system
// Variant Admin Management Entity

// variant should have a dedicated endpoint for manual stock updates

// this entity is a child for Product entity and managed via Product
// but in the JPA/Hibernate/Persistence layer they have separate lifecycle from ProductJpaEntity

@Getter
@NullMarked
public class Variant {
    private final short variantIndex;

    private VariantName variantName;

    private short stockItems; // default=0

    private int price; // toman (without discount)

    @Nullable
    private Short discountPercent; // 0-100

    private int weight; // unit: g - 1000g

    private boolean visible;

    @Nullable
    private final LocalDateTime lastOrderedAt; // fill after payment_verification

    @Nullable
    private final Short version;

    private Variant(
            short variantIndex, VariantName variantName, short stockItems, int price,
            @Nullable Short discountPercent, int weight, boolean visible, @Nullable LocalDateTime lastOrderedAt,
            @Nullable Short version
    ) {
        this.variantIndex = variantIndex;
        this.variantName = variantName;
        this.stockItems = stockItems;
        this.price = price;
        this.discountPercent = discountPercent;
        this.weight = weight;
        this.visible = visible;
        this.lastOrderedAt = lastOrderedAt;
        this.version = version;
    }

    public static Variant newVariant(
            short newVariantIndex, VariantName variantName, int price,
            @Nullable Short discountPercent, int weight
    ) {
        return new Variant(newVariantIndex, variantName, (short) 0, price, discountPercent, weight, false, null, null);
    }

    public static Variant existingVariant(
            short variantIndex, VariantName variantName, short stockItems, int price,
            @Nullable Short discountPercent, int weight, boolean visible, @Nullable LocalDateTime lastOrderedAt,
            @Nullable Short version
    ) {
        return new Variant(variantIndex, variantName, stockItems, price, discountPercent,
                weight, visible, lastOrderedAt, version);
    }

    public void editVariant(
            VariantName newVariantName, int newPrice, @Nullable Short newDiscountPercent,
            int newWeight
    ) {
        this.variantName = newVariantName;
        this.price = newPrice;
        this.discountPercent = newDiscountPercent;
        this.weight = newWeight;
    }

    public void markAsVisible() {
        this.visible = true;
    }

    public void markAsInvisible() {
        this.visible = false;
    }

    public void updateStockItems(boolean decrease, short count) {
        if (decrease) {
            this.stockItems = (short) (this.stockItems - count);
        } else {
            this.stockItems = (short) (this.stockItems + count);
        }
    }
}
