package dev.hspl.hspl2shop.shop.model.write.entity;

import dev.hspl.hspl2shop.shop.value.PaymentSessionState;
import lombok.Getter;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@NullMarked
public class PaymentSession {
    private final UUID id;

    @Nullable // can be null only if user deleted from the system
    private final UUID userId;

    @Nullable // can be null only if order deleted from the system
    private final UUID targetOrderId;

    private final String paymentWebServiceAuthority;

    private final long priceToPay;

    private PaymentSessionState state;

    @Nullable
    private String paymentBankingReference; // set after verification

    @Nullable
    private String paymentCardPan; // set after verification

    @Nullable
    private String paymentCardHash; // set after verification

    private final LocalDateTime createdAt;

    @Nullable
    private LocalDateTime paidAt;

    @Nullable
    private LocalDateTime verifiedAt;

    @Nullable
    private LocalDateTime failedAt;

    @Nullable
    private final Short version;

    private PaymentSession(
            UUID id, @Nullable UUID userId, @Nullable UUID targetOrderId, String paymentWebServiceAuthority,
            long priceToPay, PaymentSessionState state, @Nullable String paymentBankingReference,
            @Nullable String paymentCardPan, @Nullable String paymentCardHash, LocalDateTime createdAt,
            @Nullable LocalDateTime paidAt, @Nullable LocalDateTime verifiedAt,
            @Nullable LocalDateTime failedAt, @Nullable Short version
    ) {
        this.id = id;
        this.userId = userId;
        this.targetOrderId = targetOrderId;
        this.paymentWebServiceAuthority = paymentWebServiceAuthority;
        this.priceToPay = priceToPay;
        this.state = state;
        this.paymentBankingReference = paymentBankingReference;
        this.paymentCardPan = paymentCardPan;
        this.paymentCardHash = paymentCardHash;
        this.createdAt = createdAt;
        this.paidAt = paidAt;
        this.verifiedAt = verifiedAt;
        this.failedAt = failedAt;
        this.version = version;
    }

    public static PaymentSession newSession(
            UUID newSessionId, UUID userId, UUID targetOrderId, String paymentWebServiceAuthority,
            long priceToPay, LocalDateTime currentDateTime
    ) {
        return new PaymentSession(newSessionId, userId, targetOrderId, paymentWebServiceAuthority,
                priceToPay, PaymentSessionState.CREATED, null, null, null,
                currentDateTime, null, null, null, null);
    }

    public static PaymentSession existingSession(
            UUID id, @Nullable UUID userId, @Nullable UUID targetOrderId, String paymentWebServiceAuthority,
            long priceToPay, PaymentSessionState state, @Nullable String paymentBankingReference,
            @Nullable String paymentCardPan, @Nullable String paymentCardHash, LocalDateTime createdAt,
            @Nullable LocalDateTime paidAt, @Nullable LocalDateTime verifiedAt,
            @Nullable LocalDateTime failedAt, @Nullable Short version
    ) {
        return new PaymentSession(id, userId, targetOrderId, paymentWebServiceAuthority, priceToPay,
                state, paymentBankingReference, paymentCardPan, paymentCardHash, createdAt,
                paidAt, verifiedAt, failedAt, version);
    }

    public void markAsPaid(LocalDateTime currentDateTime) {
        this.state = PaymentSessionState.PAID;
        this.paidAt = currentDateTime;
    }

    public void markAsVerified(
            String paymentBankingReference, @Nullable String paymentCardPan,
            @Nullable String paymentCardHash, LocalDateTime currentDateTime
    ) {
        this.paymentBankingReference = paymentBankingReference;
        this.paymentCardPan = paymentCardPan;
        this.paymentCardHash = paymentCardHash;
        this.state = PaymentSessionState.VERIFIED;
        this.verifiedAt = currentDateTime;
    }

    public void markAsFailed(LocalDateTime currentDateTime) {
        this.state = PaymentSessionState.FAILED;
        this.failedAt = currentDateTime;
    }
}
