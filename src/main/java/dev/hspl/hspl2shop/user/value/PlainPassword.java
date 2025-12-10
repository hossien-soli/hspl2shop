package dev.hspl.hspl2shop.user.value;

import org.jspecify.annotations.NullMarked;

@NullMarked
public record PlainPassword(String value) {
    public PlainPassword {
        // TODO: add validation throw exception / min:5  max:30
    }
}
