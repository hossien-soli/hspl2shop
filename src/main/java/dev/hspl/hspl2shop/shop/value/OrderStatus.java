package dev.hspl.hspl2shop.shop.value;

public enum OrderStatus {
    REGISTERED,
    PAID, // should be verified
    PAYMENT_VERIFIED,
    PAYMENT_FAILED,
    RECEIVED_PREPARING,
    DELIVERED,
    CANCELLED,
    OUTDATED
}
