package dev.hspl.hspl2shop.user.exception;

import dev.hspl.hspl2shop.common.exception.ApplicationException;

public class InvalidProvinceIdException extends ApplicationException {
    public InvalidProvinceIdException() {
        super("provinceId should be greater than or equals to 1");
    }

    @Override
    public String problemKey() {
        return "user.address.province_id.invalid";
    }

    @Override
    public short statusCode() {
        return 400;
    }
}
