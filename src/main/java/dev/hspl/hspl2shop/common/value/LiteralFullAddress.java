package dev.hspl.hspl2shop.common.value;

import org.jspecify.annotations.NullMarked;

// full details including province, city, street, house-number, apartment unit-number and ...

@NullMarked
public record LiteralFullAddress(String value) {
    public LiteralFullAddress {
        // i think min length should be 15!!!
        // TODO: add validation for length only throw exception
    }
}
