package dev.hspl.hspl2shop.shop.model.impl.jpa.entity;

import dev.hspl.hspl2shop.shop.value.PaymentSessionState;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity(name = "PaymentSession")
@Table(name = "payment_sessions")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@NullMarked
public class PaymentSessionJpaEntity {
    @Id
    @Column(name = "id")
    private UUID id;

    @Column(name = "user_id")
    @Nullable // can be null only if user deleted from the system
    private UUID userId;

    @Column(name = "order_id")
    @Nullable // can be null only if order deleted from the system
    private UUID targetOrderId;

    @Column(name = "pg_authority")
    private String paymentWebServiceAuthority;

    @Column(name = "price")
    private long priceToPay;

    @Column(name = "state", columnDefinition = "PAYMENT_SESSION_STATE")
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Enumerated(EnumType.STRING)
    private PaymentSessionState state;

    @Column(name = "pg_ref")
    @Nullable
    private String paymentBankingReference;

    @Column(name = "pg_card_pan")
    @Nullable
    private String paymentCardPan;

    @Column(name = "pg_card_hash")
    @Nullable
    private String paymentCardHash;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "paid_at")
    @Nullable
    private LocalDateTime paidAt;

    @Column(name = "verified_at")
    @Nullable
    private LocalDateTime verifiedAt;

    @Column(name = "failed_at")
    @Nullable
    private LocalDateTime failedAt;

    @Column(name = "version")
    @Version
    @Nullable
    private Short version;
}
