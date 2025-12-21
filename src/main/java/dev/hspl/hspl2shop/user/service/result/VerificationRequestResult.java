package dev.hspl.hspl2shop.user.service.result;

import org.jspecify.annotations.NullMarked;

import java.util.UUID;

// PhoneVerificationRequestResult

@NullMarked
public record VerificationRequestResult(
        UUID verificationSessionId,
        short secondsToNextRequest
) {
}
