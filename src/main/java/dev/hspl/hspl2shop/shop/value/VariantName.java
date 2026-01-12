package dev.hspl.hspl2shop.shop.value;

import org.jspecify.annotations.NullMarked;

@NullMarked
public record VariantName(String value) {
    public VariantName {
        // TODO: add validation throw exception
    }
}
