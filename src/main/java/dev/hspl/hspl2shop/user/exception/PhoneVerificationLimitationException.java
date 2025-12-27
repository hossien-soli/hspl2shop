package dev.hspl.hspl2shop.user.exception;

import dev.hspl.hspl2shop.common.exception.ApplicationException;
import dev.hspl.hspl2shop.common.value.PhoneNumber;
import dev.hspl.hspl2shop.user.value.RequestClientIdentifier;
import lombok.Getter;
import org.jspecify.annotations.NullMarked;

@Getter
@NullMarked
public class PhoneVerificationLimitationException extends ApplicationException {
    private final PhoneNumber phoneNumber;
    private final RequestClientIdentifier requestClientIdentifier;
    private final short secondsDelayBetweenSessions;
    private final long secondsElapsed;

    public PhoneVerificationLimitationException(
            PhoneNumber phoneNumber,
            RequestClientIdentifier requestClientIdentifier,
            short secondsDelayBetweenSessions,
            long secondsElapsed
    ) {
        super("user with phone[%s] and req-client-id[%s] is requesting verification code more than limitation (delayBetweenSessions[%d] secondsElapsedFromLastUserSession[%d])".formatted(
                phoneNumber.value(),
                requestClientIdentifier.value(),
                secondsDelayBetweenSessions,
                secondsElapsed
        ));

        this.phoneNumber = phoneNumber;
        this.requestClientIdentifier = requestClientIdentifier;
        this.secondsDelayBetweenSessions = secondsDelayBetweenSessions;
        this.secondsElapsed = secondsElapsed;
    }

    @Override
    public String problemKey() {
        return "phone.verification.limitation";
    }

    @Override
    public short statusCode() {
        return 400;
    }
}
