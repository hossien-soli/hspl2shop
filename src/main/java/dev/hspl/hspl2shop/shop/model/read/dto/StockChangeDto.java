package dev.hspl.hspl2shop.shop.model.read.dto;

import org.jspecify.annotations.Nullable;

import java.time.LocalDateTime;
import java.util.UUID;

// only OWNER/MANAGER can see these records(check authenticated user role)

public record StockChangeDto(
        UUID id,

        @Nullable
        UUID relatedUserId,

        String productId, // HumanReadableId
        short variantIndex,

        String productName, // ProductName
        String variantName, // ProductName

        boolean increased,
        int stockItems, // variant stock items before change
        short count,
        String description,

        LocalDateTime createdAt
) {
}
