package dev.hspl.hspl2shop.shop.value;

import org.jspecify.annotations.NullMarked;

@NullMarked
public record ProductName(String value) {
    public ProductName {
        // TODO: add validation throw exception
    }
}
