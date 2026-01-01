package dev.hspl.hspl2shop.user.model.write.entity;

import dev.hspl.hspl2shop.user.value.ProtectedOpaqueToken;
import dev.hspl.hspl2shop.user.value.RequestClientIdentifier;
import lombok.Getter;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.time.LocalDateTime;
import java.util.UUID;

// we should have a scheduled task for deleting old finished tokens & sessions(IMPORTANT)
// if refresh token expires client should redirect user to login page

@Getter
@NullMarked
public class RefreshToken {
    private final UUID id;

    private final LoginSession loginSession; // refresh token family

    private final ProtectedOpaqueToken token;

    private short lifetimeHours; // token expiration = login-session expiration

    private boolean refreshed;

    private final LocalDateTime createdAt;

    @Nullable
    private LocalDateTime refreshedAt;

    @Nullable
    private final Short version;

    private RefreshToken(
            UUID id, LoginSession loginSession, ProtectedOpaqueToken token, short lifetimeHours,
            boolean refreshed, LocalDateTime createdAt, @Nullable LocalDateTime refreshedAt,
            @Nullable Short version
    ) {
        this.id = id;
        this.loginSession = loginSession;
        this.token = token;
        this.lifetimeHours = lifetimeHours;
        this.refreshed = refreshed;
        this.createdAt = createdAt;
        this.refreshedAt = refreshedAt;
        this.version = version;
    }

    public static RefreshToken newLogin(
            UUID newTokenId, UUID newSessionId, UUID userId, RequestClientIdentifier requestClientIdentifier,
            ProtectedOpaqueToken newToken, short lifetimeHours, LocalDateTime currentDateTime
    ) {
        var newSession = LoginSession.newSession(newSessionId, userId, requestClientIdentifier, currentDateTime);

        return new RefreshToken(newTokenId, newSession, newToken, lifetimeHours, false,
                currentDateTime, null, null);
    }

    public static RefreshToken newToken(
            UUID newTokenId, LoginSession loginSession, ProtectedOpaqueToken newToken,
            short lifetimeHours, LocalDateTime currentDateTime
    ) {
        return new RefreshToken(newTokenId, loginSession, newToken, lifetimeHours, false,
                currentDateTime, null, null);
    }

    public static RefreshToken existingToken(
            UUID id, LoginSession loginSession, ProtectedOpaqueToken token, short lifetimeHours,
            boolean refreshed, LocalDateTime createdAt, @Nullable LocalDateTime refreshedAt,
            @Nullable Short version
    ) {
        return new RefreshToken(id, loginSession, token, lifetimeHours, refreshed, createdAt,
                refreshedAt, version);
    }


}
