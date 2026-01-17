package dev.hspl.hspl2shop.shop.component.dto;

import org.jspecify.annotations.NullMarked;

@NullMarked
public record PaymentVerificationResult(
        String paymentBankingReference,

        String paymentCardPan,

        String paymentCardHash
) {
}
