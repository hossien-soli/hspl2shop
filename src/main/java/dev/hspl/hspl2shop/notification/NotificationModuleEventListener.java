package dev.hspl.hspl2shop.notification;

import dev.hspl.hspl2shop.common.event.CustomerRegistrationEvent;
import dev.hspl.hspl2shop.notification.service.SmsDeliverySystemService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

//@Component
//@RequiredArgsConstructor
public class NotificationModuleEventListener {
    //private final SmsDeliverySystemService smsDeliveryService;

    @TransactionalEventListener(fallbackExecution = true)
    @Async
    public void listenOne(CustomerRegistrationEvent event) {
        // TODO: send welcome sms/notification message to customer
    }
}
