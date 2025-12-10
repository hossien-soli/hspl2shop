package dev.hspl.hspl2shop.shop.value;

import org.jspecify.annotations.NullMarked;

@NullMarked
public record CategoryName(String value) {
    public CategoryName {
        // TODO: add validation for min and max length
    }
}
