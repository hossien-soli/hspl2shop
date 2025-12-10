package dev.hspl.hspl2shop.notification.usage.system;

import dev.hspl.hspl2shop.notification.component.SmsDeliveryAgent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SmsDeliveryService implements DeliverVerificationSmsUseCase {
    private final SmsDeliveryAgent deliveryAgent;

    @Override
    public void execute(DeliverVerificationSmsCommand command) {
        deliveryAgent.deliverVerificationSms(command.phoneNumber(), command.verificationCode());
    }
}
