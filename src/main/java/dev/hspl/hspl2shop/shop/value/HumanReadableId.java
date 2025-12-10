package dev.hspl.hspl2shop.shop.value;

import org.jspecify.annotations.NullMarked;

// it's a unique id that have meaning and readable by human (non-technical people)
// like a username
// for products it can be the slug of product(user-friendly, unique part of a product's URL)
// it should only consist of lower-case letters, numbers, -
// example: asus-laptop, apple-phone, iphone16-pro-max

@NullMarked
public record HumanReadableId(String value) {
    public HumanReadableId {
        // TODO: add validation throw exception
    }
}
