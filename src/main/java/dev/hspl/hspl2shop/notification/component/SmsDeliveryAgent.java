package dev.hspl.hspl2shop.notification.component;

import dev.hspl.hspl2shop.common.value.PhoneNumber;
import org.jspecify.annotations.NullMarked;

@NullMarked
public interface SmsDeliveryAgent {
    void deliverSimpleSms(PhoneNumber phoneNumber, String smsText);
    void deliverVerificationSms(PhoneNumber phoneNumber, String verificationCode);
}
