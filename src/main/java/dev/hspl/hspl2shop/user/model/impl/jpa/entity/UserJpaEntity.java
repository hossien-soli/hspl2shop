package dev.hspl.hspl2shop.user.model.impl.jpa.entity;

import dev.hspl.hspl2shop.common.value.UserRole;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.time.LocalDateTime;
import java.util.UUID;

// we should not put any data validation here (everything should be in domain's entity)

@Entity(name = "User")
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@NullMarked
public class UserJpaEntity {
    @Id
    @Column(name = "id")
    private UUID id;

    @Column(name = "name")
    private String fullName;

    @Column(name = "phone")
    private String phoneNumber;

    @Column(name = "password")
    private String hashedPassword;

    @Column(name = "email")
    @Nullable
    private String emailAddress;

    @Column(name = "role", columnDefinition = "USER_ROLE")
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Enumerated(EnumType.STRING)
    private UserRole role;

    @Column(name = "banned")
    private boolean banned;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @Nullable
    private LocalDateTime updatedAt;

    @Column(name = "token_refresh")
    @Nullable
    private LocalDateTime lastTokenRefresh;

    @Column(name = "version")
    @Version
    @Nullable
    private Short version;
}
