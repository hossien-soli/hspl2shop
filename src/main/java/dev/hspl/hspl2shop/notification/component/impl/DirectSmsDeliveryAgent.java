package dev.hspl.hspl2shop.notification.component.impl;

import dev.hspl.hspl2shop.common.value.PhoneNumber;
import dev.hspl.hspl2shop.notification.component.SmsDeliveryAgent;
import dev.hspl.hspl2shop.notification.component.SmsWebServiceCaller;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NullMarked;
import org.springframework.context.annotation.Description;

//@Component
@Description("directly use SmsWebServiceCaller")
@RequiredArgsConstructor
@NullMarked
public class DirectSmsDeliveryAgent implements SmsDeliveryAgent {
    private final SmsWebServiceCaller webServiceCaller;

    @Override
    public void deliverSimpleSms(PhoneNumber phoneNumber, String smsText) {
        webServiceCaller.callForSimpleSms(phoneNumber, smsText);
    }

    @Override
    public void deliverVerificationSms(PhoneNumber phoneNumber, String verificationCode) {
        webServiceCaller.callForVerificationSms(phoneNumber, verificationCode);
    }
}
