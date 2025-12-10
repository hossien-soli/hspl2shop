package dev.hspl.hspl2shop.user.value;

import org.jspecify.annotations.NullMarked;

// hashed verification code

@NullMarked
public record ProtectedVerificationCode(String value) {
    public ProtectedVerificationCode {
        // TODO: add validation throw exception
    }
}
