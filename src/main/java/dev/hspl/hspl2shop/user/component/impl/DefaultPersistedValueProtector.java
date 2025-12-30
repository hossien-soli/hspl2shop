package dev.hspl.hspl2shop.user.component.impl;

import dev.hspl.hspl2shop.user.component.PersistedValueProtector;
import dev.hspl.hspl2shop.user.value.*;
import dev.hspl.hspl2shop.common.value.PlainVerificationCode;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NullMarked;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@NullMarked
public class DefaultPersistedValueProtector implements PersistedValueProtector {
    private final PasswordEncoder passwordEncoder;

    @Override
    public ProtectedPassword protect(PlainPassword password) {
        return new ProtectedPassword(passwordEncoder.encode(password.value()));
    }

    @Override
    public boolean matches(PlainPassword password, ProtectedPassword protectedPassword) {
        return passwordEncoder.matches(password.value(), protectedPassword.value());
    }

    @Override
    public ProtectedVerificationCode protect(PlainVerificationCode verificationCode) {
        return new ProtectedVerificationCode(passwordEncoder.encode(verificationCode.value()));
    }

    @Override
    public boolean matches(PlainVerificationCode verificationCode, ProtectedVerificationCode protectedVerificationCode) {
        return passwordEncoder.matches(verificationCode.value(), protectedVerificationCode.value());
    }

    @Override
    public ProtectedOpaqueToken protect(PlainOpaqueToken opaqueToken) {
        return new ProtectedOpaqueToken(passwordEncoder.encode(opaqueToken.value()));
    }

    @Override
    public boolean matches(PlainOpaqueToken opaqueToken, ProtectedOpaqueToken protectedOpaqueToken) {
        return passwordEncoder.matches(opaqueToken.value(), protectedOpaqueToken.value());
    }
}
