package dev.hspl.hspl2shop.common.value;

import org.jspecify.annotations.NullMarked;

@NullMarked
public record EmailAddress(String value) {
    public EmailAddress {
        // TODO: add validation with exception
    }
}
