package dev.hspl.hspl2shop.shop.value;

import org.jspecify.annotations.NullMarked;

// first look description

@NullMarked
public record ShortDescription(String value) {
    public ShortDescription {
        // TODO: add validation for length
    }
}
