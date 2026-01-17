package dev.hspl.hspl2shop.shop.component;

import dev.hspl.hspl2shop.common.value.PhoneNumber;
import dev.hspl.hspl2shop.shop.component.dto.PaymentRequestResult;
import dev.hspl.hspl2shop.shop.component.dto.PaymentVerificationResult;
import org.jspecify.annotations.NullMarked;

@NullMarked
public interface PaymentWebServiceAgent {
    PaymentRequestResult requestNewPayment(
            PhoneNumber customerPhoneNumber,
            String callbackUrl,
            long priceToman
    );

    PaymentVerificationResult verifyPayment(
            String paymentWebServiceAuthority,
            long priceToman
    );
}
