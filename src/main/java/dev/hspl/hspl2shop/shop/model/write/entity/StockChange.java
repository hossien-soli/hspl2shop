package dev.hspl.hspl2shop.shop.model.write.entity;

import dev.hspl.hspl2shop.shop.value.HumanReadableId;
import lombok.Getter;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.time.LocalDateTime;
import java.util.UUID;

// only can be managed by system & OWNER, MANAGER

@Getter
@NullMarked
public class StockChange {
    private final UUID id;

    @Nullable
    private final UUID relatedUserId;

    private final HumanReadableId productId;
    private final short variantIndex; // min:1

    private final boolean increased; // false=decreased

    private final int stockItems; // variant stock items before change

    private final short count; // min:1

    private String description; // خرید کاربر

    private final LocalDateTime createdAt;

    @Nullable
    private final Short version;

    private StockChange(
            UUID id, HumanReadableId productId, short variantIndex,
            @Nullable UUID relatedUserId, boolean increased, int stockItems, short count,
            String description, LocalDateTime createdAt, @Nullable Short version
    ) {
        this.id = id;
        this.productId = productId;
        this.variantIndex = variantIndex;
        this.relatedUserId = relatedUserId;
        this.increased = increased;
        this.stockItems = stockItems;
        this.count = count;
        this.description = description;
        this.createdAt = createdAt;
        this.version = version;

        // TODO: add validation for description field length
        // TODO: add validation for count and variantIndex [>=1]
    }

    public static StockChange newChange(
            UUID newChangeId, HumanReadableId productId, short variantIndex,
            @Nullable UUID relatedUserId, boolean increased, int stockItems, short count,
            String description, LocalDateTime currentDateTime
    ) {
        return new StockChange(newChangeId, productId, variantIndex, relatedUserId, increased,
                stockItems, count, description, currentDateTime, null);
    }

    public static StockChange existingChange(
            UUID id, HumanReadableId productId, short variantIndex,
            @Nullable UUID relatedUserId, boolean increased, int stockItems, short count,
            String description, LocalDateTime createdAt, @Nullable Short version
    ) {
        return new StockChange(id, productId, variantIndex, relatedUserId, increased,
                stockItems, count, description, createdAt, version);
    }

    public void updateDescription(String newDescription) {
        this.description = newDescription;
    }
}
