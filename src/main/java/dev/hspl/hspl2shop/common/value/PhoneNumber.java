package dev.hspl.hspl2shop.common.value;

import org.jspecify.annotations.NullMarked;

@NullMarked
public record PhoneNumber(String value) {
    public PhoneNumber {
        // TODO: add validation -> min:11, max:11, starts with 09, all chars are digits
    }
}
