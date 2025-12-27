package dev.hspl.hspl2shop.notification.service;

import dev.hspl.hspl2shop.common.value.PhoneNumber;
import dev.hspl.hspl2shop.common.value.PlainVerificationCode;
import dev.hspl.hspl2shop.notification.component.SmsDeliveryAgent;
import dev.hspl.hspl2shop.notification.value.SmsText;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SmsDeliverySystemService {
    private final SmsDeliveryAgent deliveryAgent;

    public void deliverSimpleSms(PhoneNumber phoneNumber, SmsText smsText) {
        deliveryAgent.deliverSimpleSms(phoneNumber, smsText);
    }

    public void deliverVerificationSms(PhoneNumber phoneNumber, PlainVerificationCode verificationCode) {
        deliveryAgent.deliverVerificationSms(phoneNumber, verificationCode);
    }
}
