package dev.hspl.hspl2shop.shop.value;

import org.jspecify.annotations.NullMarked;

// first look description
// plain text only(not html) description / it can actually have up to 1000 characters???
// store it as text on databases not varchar

@NullMarked
public record ShortDescription(String value) {
    public ShortDescription {
        // TODO: add validation for length
    }
}
