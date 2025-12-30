package dev.hspl.hspl2shop.user.exception;

import dev.hspl.hspl2shop.common.exception.ApplicationException;
import dev.hspl.hspl2shop.common.value.PhoneNumber;
import lombok.Getter;
import org.jspecify.annotations.NullMarked;

@Getter
@NullMarked
public class InvalidVerificationSessionException extends ApplicationException {
    private final PhoneNumber phoneNumber;

    public InvalidVerificationSessionException(PhoneNumber phoneNumber) {
        super("user with phone number[%s] is trying to verify their phone with an invalid session state".formatted(
                phoneNumber.value()
        ));

        this.phoneNumber = phoneNumber;
    }

    @Override
    public String problemKey() {
        return "phone_verification.session.invalid";
    }

    @Override
    public short statusCode() {
        return 400;
    }
}
