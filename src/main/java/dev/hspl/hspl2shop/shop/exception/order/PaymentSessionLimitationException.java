package dev.hspl.hspl2shop.shop.exception.order;

import dev.hspl.hspl2shop.common.exception.ApplicationException;
import lombok.Getter;
import org.jspecify.annotations.NullMarked;

@Getter
@NullMarked
public class PaymentSessionLimitationException extends ApplicationException {
    private final short secondsDelayBetweenSessions;
    private final long secondsElapsed;

    public PaymentSessionLimitationException(short secondsDelayBetweenSessions, long secondsElapsed) {
        super("[ORDER] user is creating payment sessions more than limitation (delayBetweenSessions[%d] secondsElapsedFromLastUserSession[%d])".formatted(
                secondsDelayBetweenSessions,
                secondsElapsed
        ));

        this.secondsDelayBetweenSessions = secondsDelayBetweenSessions;
        this.secondsElapsed = secondsElapsed;
    }

    @Override
    public String problemKey() {
        return "order.payment_session.creation.limitation";
    }

    @Override
    public short statusCode() {
        return 400;
    }
}
