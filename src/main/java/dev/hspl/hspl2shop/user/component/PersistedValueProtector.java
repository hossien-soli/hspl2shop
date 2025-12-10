package dev.hspl.hspl2shop.user.component;

import dev.hspl.hspl2shop.user.value.PlainPassword;
import dev.hspl.hspl2shop.user.value.PlainVerificationCode;
import dev.hspl.hspl2shop.user.value.ProtectedPassword;
import dev.hspl.hspl2shop.user.value.ProtectedVerificationCode;
import org.jspecify.annotations.NullMarked;

// protect/hash values that should be persisted in database

@NullMarked
public interface PersistedValueProtector {
    ProtectedPassword protect(PlainPassword password);
    boolean matches(PlainPassword password, ProtectedPassword protectedPassword);

    ProtectedVerificationCode protect(PlainVerificationCode verificationCode);
    boolean matches(PlainVerificationCode verificationCode, ProtectedVerificationCode protectedVerificationCode);
}
