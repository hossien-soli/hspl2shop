package dev.hspl.hspl2shop.notification.value;

import org.jspecify.annotations.NullMarked;

@NullMarked
public record SmsText(String value) {
    public SmsText {
        // TODO: add validation on length throw exception
    }
}
