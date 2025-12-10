package dev.hspl.hspl2shop.notification.component;

import dev.hspl.hspl2shop.common.value.PhoneNumber;
import org.jspecify.annotations.NullMarked;

@NullMarked
public interface SmsWebServiceCaller {
    void callForSimpleSms(PhoneNumber phoneNumber, String smsText);
    void callForVerificationSms(PhoneNumber phoneNumber, String verificationCode);
}
