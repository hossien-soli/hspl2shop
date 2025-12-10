package dev.hspl.hspl2shop.notification;

import dev.hspl.hspl2shop.common.value.PhoneNumber;
import dev.hspl.hspl2shop.notification.usage.system.DeliverVerificationSmsCommand;
import dev.hspl.hspl2shop.notification.usage.system.DeliverVerificationSmsUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotificationModuleApi {
    private final DeliverVerificationSmsUseCase deliverVerificationSms;

    public void deliverVerificationSms(PhoneNumber phoneNumber, String verificationCode) {
        deliverVerificationSms.execute(new DeliverVerificationSmsCommand(phoneNumber, verificationCode));
    }
}
