package dev.hspl.hspl2shop.shop.component.dto;

import org.jspecify.annotations.NullMarked;

@NullMarked
public record PaymentRequestResult(
        String gatewayUrl,

        String paymentWebServiceAuthority  // شناسه یکتایی که وب سرویس پرداخت برای هر پرداخت بهمون میده
) {
}
