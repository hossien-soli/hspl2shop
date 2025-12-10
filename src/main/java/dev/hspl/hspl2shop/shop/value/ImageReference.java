package dev.hspl.hspl2shop.shop.value;

import org.jspecify.annotations.NullMarked;

// reference to a image file in blob/object storage

@NullMarked
public record ImageReference(String value) {
    public ImageReference {
        // TODO: add validation throw exception
    }
}
