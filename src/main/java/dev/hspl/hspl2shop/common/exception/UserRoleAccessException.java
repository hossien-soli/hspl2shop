package dev.hspl.hspl2shop.common.exception;

import dev.hspl.hspl2shop.common.value.UserAction;
import dev.hspl.hspl2shop.common.value.UserRole;
import lombok.Getter;
import org.jspecify.annotations.NullMarked;

@Getter
@NullMarked
public class UserRoleAccessException extends ApplicationException {
    private final UserAction userAction;
    private final UserRole userRole;

    public UserRoleAccessException(UserAction userAction, UserRole userRole) {
        super("user with role[%s] is trying to perform action[%s] but access denied!".formatted(
                userRole,
                userAction
        ));

        this.userAction = userAction;
        this.userRole = userRole;
    }

    @Override
    public String problemKey() {
        return "role_access_denied";
    }

    @Override
    public short statusCode() {
        return 403;
    }
}
