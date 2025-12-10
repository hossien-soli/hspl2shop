package dev.hspl.hspl2shop.user.value;

import org.jspecify.annotations.NullMarked;

// usually = ip address of the client

@NullMarked
public record RequestClientIdentifier(String value) {
    public RequestClientIdentifier {
        // TODO: add validation throw exception
    }
}
