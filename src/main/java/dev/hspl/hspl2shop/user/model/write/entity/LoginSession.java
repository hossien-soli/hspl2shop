package dev.hspl.hspl2shop.user.model.write.entity;

import dev.hspl.hspl2shop.user.value.LoginSessionState;
import dev.hspl.hspl2shop.user.value.RequestClientIdentifier;
import lombok.Getter;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.time.LocalDateTime;
import java.util.UUID;

// login session = refresh token family
// after each login with phoneNumber and password a new session is created
// expiration of the login session is based on the last refresh-token expiration
// this entity is managed by/inside RefreshToken entity

@Getter
@NullMarked
public class LoginSession {
    private final UUID id;

    private final UUID userId;

    private int numberOfTokenRefresh;

    private LoginSessionState state;

    private RequestClientIdentifier requestClientIdentifier; // updates after each token refresh

    private final LocalDateTime createdAt;

    @Nullable
    private LocalDateTime stateUpdatedAt;

    @Nullable
    private final Integer version; // this field should be managed by Repository implementations
    // in domain or business logic only use it for client-side concurrency control checks
    // null = entity is new and should be persisted

    private LoginSession(
            UUID id, UUID userId, int numberOfTokenRefresh, LoginSessionState state,
            RequestClientIdentifier requestClientIdentifier, LocalDateTime createdAt,
            @Nullable LocalDateTime stateUpdatedAt, @Nullable Integer version
    ) {
        this.id = id;
        this.userId = userId;
        this.numberOfTokenRefresh = numberOfTokenRefresh;
        this.state = state;
        this.requestClientIdentifier = requestClientIdentifier;
        this.createdAt = createdAt;
        this.stateUpdatedAt = stateUpdatedAt;
        this.version = version;
    }

    public static LoginSession newSession(
            UUID newSessionId, UUID userId, RequestClientIdentifier requestClientIdentifier,
            LocalDateTime currentDateTime
    ) {
        return new LoginSession(newSessionId, userId, 0, LoginSessionState.ACTIVE, requestClientIdentifier,
                currentDateTime, null, null);
    }

    public static LoginSession existingSession(
            UUID id, UUID userId, int numberOfTokenRefresh, LoginSessionState state,
            RequestClientIdentifier requestClientIdentifier, LocalDateTime createdAt,
            @Nullable LocalDateTime stateUpdatedAt, @Nullable Integer version
    ) {
        return new LoginSession(id, userId, numberOfTokenRefresh, state, requestClientIdentifier,
                createdAt, stateUpdatedAt, version);
    }


}
