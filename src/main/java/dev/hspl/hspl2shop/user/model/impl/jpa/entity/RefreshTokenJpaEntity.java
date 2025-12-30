package dev.hspl.hspl2shop.user.model.impl.jpa.entity;

import jakarta.persistence.*;
import lombok.*;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity(name = "RefreshToken")
@Table(name = "refresh_tokens")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@NullMarked
public class RefreshTokenJpaEntity {
    @Id
    @Column(name = "id")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "session_id")
    private LoginSessionJpaEntity loginSession;

    @Column(name = "token")
    private String hashedToken;

    @Column(name = "lifetime")
    private short lifetimeHours;

    @Column(name = "refreshed")
    private boolean refreshed;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "refreshed_at")
    @Nullable
    private LocalDateTime refreshedAt;

    @Column(name = "version")
    @Version
    @Nullable
    private Short version;
}
