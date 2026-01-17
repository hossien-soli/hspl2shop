package dev.hspl.hspl2shop.shop.event;

import dev.hspl.hspl2shop.common.DomainEvent;
import dev.hspl.hspl2shop.shop.model.write.entity.PaymentSession;
import org.jspecify.annotations.NullMarked;

import java.util.UUID;

@NullMarked
public record SessionMarkedAsPaidEvent(
        UUID paymentSessionId
) implements DomainEvent {
    @Override
    public String relatedEntityName() {
        return PaymentSession.class.getSimpleName();
    }

    @Override
    public String relatedEntityIdAsString() {
        return paymentSessionId.toString();
    }
}
