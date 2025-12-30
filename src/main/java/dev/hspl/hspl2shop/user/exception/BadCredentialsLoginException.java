package dev.hspl.hspl2shop.user.exception;

import dev.hspl.hspl2shop.common.exception.ApplicationException;
import dev.hspl.hspl2shop.common.value.PhoneNumber;
import lombok.Getter;
import org.jspecify.annotations.NullMarked;

@Getter
@NullMarked
public class BadCredentialsLoginException extends ApplicationException {
    private final PhoneNumber phoneNumber;

    public BadCredentialsLoginException(PhoneNumber phoneNumber) {
        super("user with phone number[%s] trying to login with bad credentials".formatted(
                phoneNumber.value()
        ));

        this.phoneNumber = phoneNumber;
    }

    @Override
    public String problemKey() {
        return "login.credentials.bad";
    }

    @Override
    public short statusCode() {
        return 400;
    }
}
