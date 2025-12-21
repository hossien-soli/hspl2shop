package dev.hspl.hspl2shop.common.value;

import org.jspecify.annotations.NullMarked;

@NullMarked
public record PostalCode(String value) {
    public PostalCode {
        // TODO: add validation throw exception
    }
}
