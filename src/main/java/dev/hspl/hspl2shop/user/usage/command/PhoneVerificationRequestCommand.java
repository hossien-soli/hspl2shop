package dev.hspl.hspl2shop.user.usage.command;

import dev.hspl.hspl2shop.common.value.PhoneNumber;
import dev.hspl.hspl2shop.user.value.PhoneVerificationPurpose;
import dev.hspl.hspl2shop.user.value.RequestClientIdentifier;
import org.jspecify.annotations.NullMarked;

@NullMarked
public record PhoneVerificationRequestCommand(
        PhoneNumber phoneNumber,
        RequestClientIdentifier requestClientIdentifier,
        PhoneVerificationPurpose purpose
) {
}
