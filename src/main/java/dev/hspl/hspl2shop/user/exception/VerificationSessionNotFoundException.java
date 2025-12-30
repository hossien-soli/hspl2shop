package dev.hspl.hspl2shop.user.exception;

import dev.hspl.hspl2shop.common.exception.ApplicationException;

public class VerificationSessionNotFoundException extends ApplicationException {
    public VerificationSessionNotFoundException() {
        super("the session id provided by client is invalid and no session exists");
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
