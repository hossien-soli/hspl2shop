package dev.hspl.hspl2shop.user.component;

import dev.hspl.hspl2shop.user.value.*;
import dev.hspl.hspl2shop.common.value.PlainVerificationCode;
import org.jspecify.annotations.NullMarked;

// protect/hash values that should be persisted in database

@NullMarked
public interface PersistedValueProtector {
    ProtectedPassword protect(PlainPassword password);
    boolean matches(PlainPassword password, ProtectedPassword protectedPassword);

    ProtectedVerificationCode protect(PlainVerificationCode verificationCode);
    boolean matches(PlainVerificationCode verificationCode, ProtectedVerificationCode protectedVerificationCode);

    ProtectedOpaqueToken protect(PlainOpaqueToken opaqueToken);
    boolean matches(PlainOpaqueToken opaqueToken, ProtectedOpaqueToken protectedOpaqueToken);
}
