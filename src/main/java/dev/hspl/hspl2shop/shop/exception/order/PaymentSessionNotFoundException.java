package dev.hspl.hspl2shop.shop.exception.order;

import dev.hspl.hspl2shop.common.exception.ApplicationException;
import lombok.Getter;
import org.jspecify.annotations.NullMarked;

import java.util.UUID;

@Getter
@NullMarked
public class PaymentSessionNotFoundException extends ApplicationException {
    private final UUID paymentSessionId;

    public PaymentSessionNotFoundException(UUID paymentSessionId) {
        super("[ORDER] user or system is trying to fetch a payment session with id[%s] that doesn't exists".formatted(
                paymentSessionId.toString()
        ));

        this.paymentSessionId = paymentSessionId;
    }

    @Override
    public String problemKey() {
        return "order.payment_session.entity.not_found";
    }

    @Override
    public short statusCode() {
        return 400;
    }
}
