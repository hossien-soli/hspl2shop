package dev.hspl.hspl2shop.user.exception;

import dev.hspl.hspl2shop.common.exception.ApplicationException;
import lombok.Getter;
import org.jspecify.annotations.NullMarked;

@Getter
@NullMarked
public class InvalidClientRefreshTokenException extends ApplicationException {
    private final String clientRefreshToken;

    public InvalidClientRefreshTokenException(String clientRefreshToken) {
        super("provided client refresh token[%s] is not valid".formatted(clientRefreshToken));

        this.clientRefreshToken = clientRefreshToken;
    }

    @Override
    public String problemKey() {
        return "auth.client_refresh_token.invalid";
    }

    @Override
    public short statusCode() {
        return 400;
    }
}
