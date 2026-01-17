package dev.hspl.hspl2shop.shop.value;

import org.jspecify.annotations.NullMarked;

@NullMarked
public record DeliveryMethodDescription(String value) {
    public DeliveryMethodDescription {
        // max:250 chars (db table column is varchar(255))
        // TODO: add validation throw generic validation exception
    }
}
