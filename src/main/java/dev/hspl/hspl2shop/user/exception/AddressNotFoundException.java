package dev.hspl.hspl2shop.user.exception;

import dev.hspl.hspl2shop.common.exception.ApplicationException;

public class AddressNotFoundException extends ApplicationException {
    public AddressNotFoundException() {
        super("user is trying to fetch an address with invalid id or an address of another user!");
    }

    @Override
    public String problemKey() {
        return "user.address.not_found";
    }

    @Override
    public short statusCode() {
        return 404;
    }
}
