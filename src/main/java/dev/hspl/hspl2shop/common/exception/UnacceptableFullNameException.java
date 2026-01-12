package dev.hspl.hspl2shop.common.exception;

import lombok.Getter;

// TODO: remove this exception and use generic global validation exception for all value objects validations

@Getter
public class UnacceptableFullNameException extends ApplicationException {
    private final String inputValue;

    public UnacceptableFullNameException(String inputValue) {
        super("cannot create a FullName value object from user input '%s'".formatted(inputValue));
        this.inputValue = inputValue;
    }

    @Override
    public String problemKey() {
        return "user.full_name.unacceptable";
    }

    @Override
    public short statusCode() {
        return 400;
    }
}
