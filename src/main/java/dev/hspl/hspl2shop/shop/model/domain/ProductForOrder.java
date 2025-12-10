package dev.hspl.hspl2shop.shop.model.domain;

import dev.hspl.hspl2shop.shop.value.CategoryInfo;
import dev.hspl.hspl2shop.shop.value.HumanReadableId;
import dev.hspl.hspl2shop.shop.value.ProductName;
import lombok.Getter;
import org.jspecify.annotations.NullMarked;

import java.util.Set;

// domain required product info for creating and handling orders

@Getter
@NullMarked
public class ProductForOrder {
    private HumanReadableId id;
    private short variantIndex;

    private ProductName productName;

    private String variantName;


}
