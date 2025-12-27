package dev.hspl.hspl2shop.common.exception;

import org.springframework.security.core.Authentication;

public class UnsupportedAuthUserException extends ApplicationException {
    public UnsupportedAuthUserException(Authentication authentication) {
        super("auth type[%s] with principal type[%s] is not supported in app controllers!".formatted(
                authentication.getClass().getSimpleName(),
                authentication.getPrincipal() != null ?
                        authentication.getPrincipal().getClass().getSimpleName() : "null"
        ));
    }

    @Override
    public String problemKey() {
        return "contact_support_message";
    }

    @Override
    public short statusCode() {
        return 401;
    }
}
