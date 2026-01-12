package dev.hspl.hspl2shop.shop.value;

import org.jspecify.annotations.NullMarked;

// reference to an external file in blob/object storage
// it can be an image or a html file for long descriptions

@NullMarked
public record ExternalFileReference(String value) {
    public ExternalFileReference {
        // TODO: add validation throw exception
    }
}
