package dev.hspl.hspl2shop.user.model.impl.jpa.entity;

import dev.hspl.hspl2shop.user.value.PhoneVerificationPurpose;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.time.LocalDateTime;
import java.util.UUID;

// we should not put any data validation here (everything should be in domain's entity)

@Entity(name = "VerificationSession")
@Table(name = "verification_sessions")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@NullMarked
public class VerificationSessionJpaEntity {
    @Id
    @Column(name = "id")
    private UUID id;

    @Column(name = "phone")
    private String phoneNumber;

    @Column(name = "code")
    private String hashedVerificationCode;

    @Column(name = "request_client_id")
    private String requestClientIdentifier;

    @Column(name = "purpose", columnDefinition = "VERIFICATION_PURPOSE")
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Enumerated(EnumType.STRING)
    private PhoneVerificationPurpose purpose;

    @Column(name = "verified")
    private boolean verified;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "version")
    @Version
    @Nullable
    private Short version;
}
