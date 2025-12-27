package dev.hspl.hspl2shop.user.exception;

import dev.hspl.hspl2shop.common.exception.ApplicationException;
import dev.hspl.hspl2shop.common.value.PhoneNumber;
import lombok.Getter;
import org.jspecify.annotations.NullMarked;

@Getter
@NullMarked
public class PhoneNotRegisteredException extends ApplicationException {
    private final PhoneNumber phoneNumber;

    public PhoneNotRegisteredException(PhoneNumber phoneNumber) {
        super("user is requesting verification code for reset-password/change-phone-number but the phone number[%s] is not registered".formatted(
                phoneNumber.value()
        ));

        this.phoneNumber = phoneNumber;
    }

    @Override
    public String problemKey() {
        return "user.phone.not_registered";
    }

    @Override
    public short statusCode() {
        return 400;
    }
}
