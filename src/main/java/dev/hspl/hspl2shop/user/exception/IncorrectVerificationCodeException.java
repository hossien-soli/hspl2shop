package dev.hspl.hspl2shop.user.exception;

import dev.hspl.hspl2shop.common.exception.ApplicationException;
import dev.hspl.hspl2shop.common.value.PhoneNumber;
import lombok.Getter;
import org.jspecify.annotations.NullMarked;

@Getter
@NullMarked
public class IncorrectVerificationCodeException extends ApplicationException {
    private final PhoneNumber phoneNumber;

    public IncorrectVerificationCodeException(PhoneNumber phoneNumber) {
        super("user with phone number[%s] is trying to verify session with incorrect code".formatted(
                phoneNumber.value()
        ));

        this.phoneNumber = phoneNumber;
    }

    @Override
    public String problemKey() {
        return "phone_verification.code.incorrect";
    }

    @Override
    public short statusCode() {
        return 400;
    }
}
