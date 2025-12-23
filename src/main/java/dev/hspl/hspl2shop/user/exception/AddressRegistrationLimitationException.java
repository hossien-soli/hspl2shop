package dev.hspl.hspl2shop.user.exception;

import dev.hspl.hspl2shop.common.exception.ApplicationException;

public class AddressRegistrationLimitationException extends ApplicationException {
    public AddressRegistrationLimitationException() {
        super("user is trying to register address more than limitation");
    }

    @Override
    public String problemKey() {
        return "user.address_registration.limitation";
    }

    @Override
    public short statusCode() {
        return 400;
    }
}
