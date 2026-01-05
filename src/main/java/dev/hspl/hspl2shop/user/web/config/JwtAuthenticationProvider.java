package dev.hspl.hspl2shop.user.web.config;

import dev.hspl.hspl2shop.common.ApplicationUser;
import dev.hspl.hspl2shop.user.component.ApplicationUserJwtService;
import dev.hspl.hspl2shop.user.exception.JwtTokenVerificationException;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

@NullMarked
@RequiredArgsConstructor
public class JwtAuthenticationProvider implements AuthenticationProvider {
    private final ApplicationUserJwtService jwtService;

    @Override
    @Nullable
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String jwtAccessToken = (String) authentication.getCredentials();

        try {
            ApplicationUser user = jwtService.validateTokenAndExtractUserInfo(jwtAccessToken);
            return JwtAuthentication.authenticated(jwtAccessToken, user);
        } catch (JwtTokenVerificationException exception) {
            throw new BadCredentialsException("jwt access token verification failed!");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return JwtAuthentication.class.isAssignableFrom(authentication);
    }
}
