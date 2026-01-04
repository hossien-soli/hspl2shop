package dev.hspl.hspl2shop.common.component.impl;

import dev.hspl.hspl2shop.common.component.ApplicationAttributeProvider;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NullMarked;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
@NullMarked
@RequiredArgsConstructor
public class ConfigFileApplicationAttributeProvider implements ApplicationAttributeProvider {
    @Value("${app.rule-user-max-address-allowed}")
    private short _ruleUserMaxAddressAllowed;

    @Value("${app.delay-limit-between-verification-sessions}")
    private short _delayLimitBetweenVerificationSessions;

    @Value("${app.verification-session-lifetime}")
    private short _verificationSessionLifetime;

    @Value("${app.refresh-token-lifetime-hours}")
    private short _refreshTokenLifetimeHours;

    @Value("${app.jwt-signature-secret}")
    private String _jwtSignatureSecret;

    @Value("${app.access-token-lifetime-minutes}")
    private short _accessTokenLifetimeMinutes;

    @Value("${app.opaque-token-random-bytes-count}")
    private short _opaqueTokenRandomBytesCount;

    @Override
    public short ruleUserMaxAddressAllowed() {
        return _ruleUserMaxAddressAllowed;
    }

    @Override
    public short delayLimitBetweenVerificationSessions() {
        return _delayLimitBetweenVerificationSessions;
    }

    @Override
    public short verificationSessionLifetime() {
        return _verificationSessionLifetime;
    }

    @Override
    public short refreshTokenLifetimeHours() {
        return _refreshTokenLifetimeHours;
    }

    @Override
    public String jwtSignatureSecret() {
        return _jwtSignatureSecret;
    }

    @Override
    public short accessTokenLifetimeMinutes() {
        return _accessTokenLifetimeMinutes;
    }

    @Override
    public short opaqueTokenRandomBytesCount() {
        return _opaqueTokenRandomBytesCount;
    }
}
