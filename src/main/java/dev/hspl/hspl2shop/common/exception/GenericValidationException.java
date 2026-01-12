package dev.hspl.hspl2shop.common.exception;

import org.jspecify.annotations.NullMarked;

// exception used for all value objects and entity basic fields validations

@NullMarked
public class GenericValidationException extends ApplicationException {
    private final String problemKey;

    public GenericValidationException(String problemKey, String exceptionMessage) {
        super(exceptionMessage);

        this.problemKey = problemKey;
    }

    @Override
    public String problemKey() {
        return problemKey;
    }

    @Override
    public short statusCode() {
        return 400;
    }
}
