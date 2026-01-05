package dev.hspl.hspl2shop.user.component;

import dev.hspl.hspl2shop.common.ApplicationUser;
import dev.hspl.hspl2shop.common.value.FullName;
import dev.hspl.hspl2shop.common.value.PhoneNumber;
import dev.hspl.hspl2shop.common.value.UserRole;
import dev.hspl.hspl2shop.user.exception.JwtTokenVerificationException;

import java.util.UUID;

public record JwtPayloadApplicationUser(
        String userId,
        String userFullName,
        String userPhoneNumber,
        String userRole,
        Boolean isUserAccountActive
) implements ApplicationUser {
    public JwtPayloadApplicationUser {
        boolean validate = userId != null && userFullName != null && userPhoneNumber != null
                && userRole != null && isUserAccountActive != null;

        if (!validate) {
            throw new JwtTokenVerificationException("payload is not as expected");
        }
    }

    @Override
    public UUID id() {
        return UUID.fromString(userId);
    }

    @Override
    public FullName fullName() {
        return new FullName(userFullName);
    }

    @Override
    public PhoneNumber phoneNumber() {
        return new PhoneNumber(userPhoneNumber);
    }

    @Override
    public UserRole role() {
        return UserRole.valueOf(userRole);
    }

    @Override
    public boolean isAccountActive() {
        return isUserAccountActive;
    }
}
