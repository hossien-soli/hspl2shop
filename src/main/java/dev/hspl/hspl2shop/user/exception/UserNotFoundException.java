package dev.hspl.hspl2shop.user.exception;

import dev.hspl.hspl2shop.common.exception.ApplicationException;
import lombok.Getter;
import org.jspecify.annotations.NullMarked;

@Getter
@NullMarked
public class UserNotFoundException extends ApplicationException {
    private final String action;

    public UserNotFoundException(String action) {
        super("no user found for performing the action[%s]".formatted(action));
        this.action = action;
    }

    @Override
    public String problemKey() {
        return "user.entity.not_found";
    }

    @Override
    public short statusCode() {
        return 400;
    }
}
