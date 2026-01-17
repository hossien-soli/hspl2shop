package dev.hspl.hspl2shop.payment;

import org.jspecify.annotations.NullMarked;

import java.util.UUID;

@NullMarked
public record PaymentVerificationRequestedEvent(
        UUID orderId
) {
}
