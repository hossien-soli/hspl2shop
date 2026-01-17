package dev.hspl.hspl2shop.shop.value;

public enum PaymentSessionState {
    CREATED,
    PAID, // should be verified
    VERIFIED, // payment verified
    FAILED
}
