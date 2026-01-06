package dev.hspl.hspl2shop.shop.model.write.entity;

import dev.hspl.hspl2shop.shop.value.HumanReadableId;
import dev.hspl.hspl2shop.shop.value.ProductName;
import lombok.Getter;
import org.jspecify.annotations.NullMarked;

// domain required product info for creating and handling orders for customers

@Getter
@NullMarked
public class ProductForOrder {
    private final HumanReadableId id;
    private final short variantIndex;

    private final String productName;
    private final String variantName;


}
