package dev.hspl.hspl2shop.common.exception;

import dev.hspl.hspl2shop.common.value.UserAction;
import lombok.Getter;
import org.jspecify.annotations.NullMarked;

@Getter
@NullMarked
public class AccountStateException extends ApplicationException {
    private final UserAction action;

    public AccountStateException(UserAction action) {
        super("user is trying to perform action[%s] but account is not active!".formatted(action));
        this.action = action;
    }

    @Override
    public String problemKey() {
        return "common.user_account.banned";
    }

    @Override
    public short statusCode() {
        return 403;
    }
}
