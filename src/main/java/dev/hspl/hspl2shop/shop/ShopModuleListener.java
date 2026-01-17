package dev.hspl.hspl2shop.shop;

import dev.hspl.hspl2shop.payment.PaymentVerificationRequestedEvent;
import dev.hspl.hspl2shop.payment.PaymentVerifiedEvent;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NullMarked;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@NullMarked
public class ShopModuleListener {

    public void listenOne(PaymentVerificationRequestedEvent event) {
        // mark order as paid
        // this should be part of active transaction
    }

    public void listenTwo(PaymentVerifiedEvent event) {
        // mark order as payment_verified
        // update product stock
        // emit new finalized order event for admins
    }
}
