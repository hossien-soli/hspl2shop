package dev.hspl.hspl2shop.user.exception;

import dev.hspl.hspl2shop.common.exception.ApplicationException;
import dev.hspl.hspl2shop.common.value.PhoneNumber;
import lombok.Getter;
import org.jspecify.annotations.NullMarked;

@Getter
@NullMarked
public class PhoneAlreadyRegisteredException extends ApplicationException {
    private final PhoneNumber phoneNumber;
    private final boolean onStepOne; // step1 = request new verification code / step2 = finalize registration

    public PhoneAlreadyRegisteredException(PhoneNumber phoneNumber, boolean onStepOne) {
        super("user is trying to register with phone number[%s] that already registered on system on step %s".formatted(
                phoneNumber.value(),
                onStepOne ? "ONE" : "TWO"
        ));

        this.phoneNumber = phoneNumber;
        this.onStepOne = onStepOne;
    }

    @Override
    public String problemKey() {
        return "user.registration.phone_exists";
    }

    @Override
    public short statusCode() {
        return 400;
    }
}
