package dev.hspl.hspl2shop.common;

import dev.hspl.hspl2shop.common.value.EmailAddress;
import dev.hspl.hspl2shop.common.value.FullName;
import dev.hspl.hspl2shop.common.value.PhoneNumber;
import dev.hspl.hspl2shop.common.value.UserRole;

import java.util.UUID;

// The domain or business logic interacts with the user through this

public interface DomainUser {
    UUID id();

    FullName fullName();

    PhoneNumber phoneNumber();

    EmailAddress emailAddress();

    UserRole role();

    boolean isAccountActive();
}
