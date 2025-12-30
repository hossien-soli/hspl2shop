package dev.hspl.hspl2shop.user.model.impl.jpa.entity;

import dev.hspl.hspl2shop.user.value.LoginSessionState;
import jakarta.persistence.*;
import lombok.*;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity(name = "LoginSession")
@Table(name = "login_sessions")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@NullMarked
public class LoginSessionJpaEntity {
    @Id
    @Column(name = "id")
    private UUID id;

    @Column(name = "user_id")
    private UUID userId;

    @Column(name = "refresh_count")
    private int numberOfTokenRefresh;

    @Column(name = "state")
    @Enumerated(EnumType.STRING)
    private LoginSessionState state;

    @Column(name = "request_client_id")
    private String requestClientIdentifier;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "state_updated_at")
    @Nullable
    private LocalDateTime stateUpdatedAt;

    @Column(name = "version")
    @Version
    @Nullable
    private Integer version;
}
