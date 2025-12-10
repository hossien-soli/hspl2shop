package dev.hspl.hspl2shop.notification.component.impl;

import dev.hspl.hspl2shop.common.value.PhoneNumber;
import dev.hspl.hspl2shop.notification.component.SmsDeliveryAgent;
import org.jspecify.annotations.NullMarked;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("prod")
@NullMarked
public class OutboxingSmsDeliveryAgent implements SmsDeliveryAgent {

    @Override
    public void deliverSimpleSms(PhoneNumber phoneNumber, String smsText) {

    }

    @Override
    public void deliverVerificationSms(PhoneNumber phoneNumber, String verificationCode) {

    }
}
