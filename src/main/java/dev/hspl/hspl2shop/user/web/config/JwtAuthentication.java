package dev.hspl.hspl2shop.user.web.config;

import dev.hspl.hspl2shop.common.ApplicationUser;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import java.util.Collection;

public class JwtAuthentication implements Authentication {
    private final boolean authenticated;
    private final String jwtAccessToken;
    private final ApplicationUser user;

    private JwtAuthentication(boolean authenticated, String jwtAccessToken, ApplicationUser user) {
        this.authenticated = authenticated;
        this.jwtAccessToken = jwtAccessToken;
        this.user = user;
    }

    public static JwtAuthentication authenticated(String jwtAccessToken, ApplicationUser user) {
        return new JwtAuthentication(true, jwtAccessToken, user);
    }

    public static JwtAuthentication unauthenticated(String jwtAccessToken) {
        return new JwtAuthentication(false, jwtAccessToken, null);
    }

    @Override
    public boolean isAuthenticated() {
        return authenticated;
    }

    @Override
    @Nullable
    public Object getPrincipal() {
        return user;
    }

    @Override
    public String getName() {
        return user.phoneNumber().value();
    }

    @Override
    @NonNull
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return AuthorityUtils.createAuthorityList("ROLE_" + user.role());
    }

    @Override
    @Nullable
    public Object getCredentials() {
        return jwtAccessToken;
    }

    @Override
    @Nullable
    public Object getDetails() {
        return null;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        throw new IllegalArgumentException("whops this object(JwtAuthentication) is immutable!");
    }
}
