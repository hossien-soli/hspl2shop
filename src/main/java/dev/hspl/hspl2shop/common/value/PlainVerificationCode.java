package dev.hspl.hspl2shop.common.value;

import org.jspecify.annotations.NullMarked;

@NullMarked
public record PlainVerificationCode(String value) {
    public PlainVerificationCode {
        // TODO: add validation throw exception
    }
}
