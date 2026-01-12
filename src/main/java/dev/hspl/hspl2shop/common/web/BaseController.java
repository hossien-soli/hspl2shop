package dev.hspl.hspl2shop.common.web;

import dev.hspl.hspl2shop.common.ApplicationUser;
import dev.hspl.hspl2shop.common.exception.UnsupportedAuthUserException;
import org.springframework.security.core.Authentication;

public abstract class BaseController {
    // TODO: update AddressController to also using this method
    protected ApplicationUser extractUser(Authentication authentication) {
        if (authentication.getPrincipal() instanceof ApplicationUser user) {
            return user;
        }

        throw new UnsupportedAuthUserException(authentication);
    }
}
