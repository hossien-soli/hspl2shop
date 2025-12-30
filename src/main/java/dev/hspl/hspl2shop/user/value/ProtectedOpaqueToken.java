package dev.hspl.hspl2shop.user.value;

import org.jspecify.annotations.NullMarked;

// hashed opaque token for persistence

@NullMarked
public record ProtectedOpaqueToken(String value) {
    public ProtectedOpaqueToken {
        // TODO: add validation throw exception
    }
}
