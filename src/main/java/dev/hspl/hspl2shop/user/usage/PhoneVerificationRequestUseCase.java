package dev.hspl.hspl2shop.user.usage;

import dev.hspl.hspl2shop.user.usage.command.PhoneVerificationRequestCommand;
import dev.hspl.hspl2shop.user.usage.result.PhoneVerificationRequestResult;
import org.jspecify.annotations.NullMarked;

@NullMarked
public interface PhoneVerificationRequestUseCase {
    PhoneVerificationRequestResult execute(PhoneVerificationRequestCommand command);
}
