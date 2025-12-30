package dev.hspl.hspl2shop.user.model.write.entity;

import dev.hspl.hspl2shop.common.value.PhoneNumber;
import dev.hspl.hspl2shop.user.exception.InvalidVerificationSessionException;
import dev.hspl.hspl2shop.user.value.PhoneVerificationPurpose;
import dev.hspl.hspl2shop.user.value.ProtectedVerificationCode;
import dev.hspl.hspl2shop.user.value.RequestClientIdentifier;
import lombok.Getter;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

// VerificationSession = PhoneVerificationSession

@Getter
@NullMarked
public class VerificationSession {
    private final UUID id;

    private final PhoneNumber phoneNumber;

    private final ProtectedVerificationCode verificationCode;

    private final RequestClientIdentifier requestClientIdentifier; // usually is ip address

    private final PhoneVerificationPurpose purpose;

    private boolean verified;

    private final LocalDateTime createdAt;

    @Nullable
    private final Short version; // this field should be managed by Repository implementations
    // null = entity is new and should be persisted

    private VerificationSession(
            UUID id, PhoneNumber phoneNumber,
            ProtectedVerificationCode verificationCode,
            RequestClientIdentifier requestClientIdentifier,
            PhoneVerificationPurpose purpose,
            boolean verified, LocalDateTime createdAt, @Nullable Short version
    ) {
        this.id = id;
        this.phoneNumber = phoneNumber;
        this.verificationCode = verificationCode;
        this.requestClientIdentifier = requestClientIdentifier;
        this.purpose = purpose;
        this.verified = verified;
        this.createdAt = createdAt;
        this.version = version;
    }

    public static VerificationSession newSession(
            UUID newId, PhoneNumber phoneNumber, ProtectedVerificationCode verificationCode,
            RequestClientIdentifier requestClientIdentifier, PhoneVerificationPurpose purpose,
            LocalDateTime currentDateTime
    ) {
        return new VerificationSession(newId, phoneNumber, verificationCode, requestClientIdentifier,
                purpose, false, currentDateTime, null);
    }

    public static VerificationSession existingSession(
            UUID id, PhoneNumber phoneNumber,
            ProtectedVerificationCode verificationCode,
            RequestClientIdentifier requestClientIdentifier,
            PhoneVerificationPurpose purpose,
            boolean verified, LocalDateTime createdAt, @Nullable Short version
    ) {
        return new VerificationSession(id, phoneNumber, verificationCode, requestClientIdentifier,
                purpose, verified, createdAt, version);
    }

    public void checkVerifiable(
            PhoneVerificationPurpose purpose,
            RequestClientIdentifier requestClientIdentifier,
            LocalDateTime currentDateTime,
            short verificationSessionLifetime // seconds
    ) {
        long secondsElapsed = Math.abs(Duration.between(this.createdAt, currentDateTime).toSeconds());

        boolean validate = !this.verified && this.purpose.equals(purpose) &&
                this.requestClientIdentifier.equals(requestClientIdentifier) &&
                secondsElapsed <= verificationSessionLifetime;

        if (!validate) {
            throw new InvalidVerificationSessionException(this.phoneNumber);
        }
    }

    public void markAsVerified() {
        this.verified = true;
    }
}
