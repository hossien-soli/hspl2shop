package dev.hspl.hspl2shop.user.value;

import org.jspecify.annotations.NullMarked;

// hashed password

@NullMarked
public record ProtectedPassword(String value) {
    public ProtectedPassword {
        // TODO: add validation throw exception
    }
}
