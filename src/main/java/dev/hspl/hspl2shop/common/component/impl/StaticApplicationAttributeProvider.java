package dev.hspl.hspl2shop.common.component.impl;

import dev.hspl.hspl2shop.common.component.ApplicationAttributeProvider;
import org.jspecify.annotations.NullMarked;
import org.springframework.stereotype.Component;

// better impl: ConfigFileApplicationAttributeProvider
// it provides attribute/property/custom-config for domain and application

@Component
@NullMarked
public class StaticApplicationAttributeProvider implements ApplicationAttributeProvider {
    @Override
    public short ruleUserMaxAddressAllowed() {
        return 10;
    }

    @Override
    public short delayLimitBetweenVerificationSessions() {
        return 90; // seconds
    }

    @Override
    public short verificationSessionLifetime() {
        return 4 * 60; // seconds
    }

    @Override
    public short refreshTokenLifetimeHours() {
        return 30 * 24; // hours
    }

    @Override
    public String jwtSignatureSecret() {
        return "ops not here!!!";
    }

    @Override
    public short accessTokenLifetimeMinutes() {
        return 45; // minutes
    }

    @Override
    public short opaqueTokenRandomBytesCount() {
        return 25;
    }

    @Override
    public short orderMaxItems() {
        return 30;
    }

    @Override
    public short orderProductVariantMaxItems() {
        return 5;
    }

    @Override
    public short orderMaxWeightKG() {
        return 80;
    }

    @Override
    public long orderMaxPrice() {
        return 500_000_000L;
    }

    @Override
    public short orderLifetimeMinutes() {
        return 60;
    }

    @Override
    public String paymentGatewayCallbackUrl() {
        return "google.com";
    }

    @Override
    public short orderProductCheckDelaySeconds() {
        return 5 * 60;
    }

    @Override
    public short delayLimitBetweenPaymentSessions() {
        return 60; // seconds
    }
}
