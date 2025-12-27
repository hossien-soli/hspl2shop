package dev.hspl.hspl2shop.notification;

import dev.hspl.hspl2shop.common.value.PhoneNumber;
import dev.hspl.hspl2shop.common.value.PlainVerificationCode;
import dev.hspl.hspl2shop.notification.service.SmsDeliverySystemService;
import dev.hspl.hspl2shop.notification.value.SmsText;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotificationModuleApi {
    private final SmsDeliverySystemService smsDeliverySystemService;

    public void deliverSimpleSms(PhoneNumber phoneNumber, String smsText) {
        smsDeliverySystemService.deliverSimpleSms(phoneNumber, new SmsText(smsText));
    }

    public void deliverVerificationSms(PhoneNumber phoneNumber, PlainVerificationCode verificationCode) {
        smsDeliverySystemService.deliverVerificationSms(phoneNumber, verificationCode);
    }
}
