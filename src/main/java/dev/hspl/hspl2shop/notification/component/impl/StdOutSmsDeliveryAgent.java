package dev.hspl.hspl2shop.notification.component.impl;

import dev.hspl.hspl2shop.common.value.PhoneNumber;
import dev.hspl.hspl2shop.common.value.PlainVerificationCode;
import dev.hspl.hspl2shop.notification.component.SmsDeliveryAgent;
import dev.hspl.hspl2shop.notification.value.SmsText;
import org.jspecify.annotations.NullMarked;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile({"default", "dev"})
@NullMarked
public class StdOutSmsDeliveryAgent implements SmsDeliveryAgent {
    @Override
    public void deliverSimpleSms(PhoneNumber phoneNumber, SmsText smsText) {
        System.out.printf("SMS [%s]-[%s]%n", phoneNumber.value(), smsText.value());
    }

    @Override
    public void deliverVerificationSms(PhoneNumber phoneNumber, PlainVerificationCode verificationCode) {
        System.out.printf("SMS [%s]-[%s]%n", phoneNumber.value(), verificationCode.value());
    }
}
