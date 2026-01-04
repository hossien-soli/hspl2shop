package dev.hspl.hspl2shop.user.exception;

import dev.hspl.hspl2shop.common.exception.ApplicationException;
import dev.hspl.hspl2shop.user.value.LoginSessionState;
import lombok.Getter;
import org.jspecify.annotations.NullMarked;

import java.util.UUID;

@Getter
@NullMarked
public class InvalidLoginSessionStateException extends ApplicationException {
    private final UUID userId;
    private final UUID refreshTokenId;
    private final LoginSessionState sessionState;

    public InvalidLoginSessionStateException(UUID userId, UUID refreshTokenId, LoginSessionState sessionState) {
        super("user with id[%s] is trying to refresh the token with id[%s] but state[%s] is invalid".formatted(
                userId.toString(),
                refreshTokenId.toString(),
                sessionState.toString()
        ));

        this.userId = userId;
        this.refreshTokenId = refreshTokenId;
        this.sessionState = sessionState;
    }

    @Override
    public String problemKey() {
        return "auth.login_session.invalid";
    }

    @Override
    public short statusCode() {
        return 400;
    }
}
