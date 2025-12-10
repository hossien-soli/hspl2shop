package dev.hspl.hspl2shop.notification.usage.system;

import dev.hspl.hspl2shop.common.value.PhoneNumber;
import org.jspecify.annotations.NullMarked;

@NullMarked
public record DeliverVerificationSmsCommand(
        PhoneNumber phoneNumber,
        String verificationCode
) {
}
