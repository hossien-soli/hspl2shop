package dev.hspl.hspl2shop.user.exception;

import dev.hspl.hspl2shop.common.exception.ApplicationException;

public class TokenRefreshFailedException extends ApplicationException {
    public TokenRefreshFailedException() {
        super("failed to refresh the token EXPIRED/REUSE_DETECTED");
    }

    @Override
    public String problemKey() {
        return "auth.token_refresh.failed";
    }

    @Override
    public short statusCode() {
        return 400;
    }
}
