package dev.hspl.hspl2shop.notification.component;

import dev.hspl.hspl2shop.common.value.PhoneNumber;
import dev.hspl.hspl2shop.common.value.PlainVerificationCode;
import dev.hspl.hspl2shop.notification.value.SmsText;
import org.jspecify.annotations.NullMarked;

@NullMarked
public interface SmsDeliveryAgent {
    void deliverSimpleSms(PhoneNumber phoneNumber, SmsText smsText);

    void deliverVerificationSms(PhoneNumber phoneNumber, PlainVerificationCode verificationCode);
}
