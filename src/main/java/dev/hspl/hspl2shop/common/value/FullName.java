package dev.hspl.hspl2shop.common.value;

import dev.hspl.hspl2shop.common.exception.UnacceptableFullNameException;
import org.jspecify.annotations.NullMarked;

@NullMarked
public record FullName(String value) {
    public FullName {
        boolean validate = value.length() >= 5 && value.length() <= 40;
        if (!validate) {
            throw new UnacceptableFullNameException(value);
        }
    }
}
