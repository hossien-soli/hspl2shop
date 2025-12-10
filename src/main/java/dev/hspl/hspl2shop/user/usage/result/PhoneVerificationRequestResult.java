package dev.hspl.hspl2shop.user.usage.result;

import org.jspecify.annotations.NullMarked;

import java.util.UUID;

@NullMarked
public record PhoneVerificationRequestResult(
        UUID verificationSessionId,
        short secondsToNextRequest
) {
}
