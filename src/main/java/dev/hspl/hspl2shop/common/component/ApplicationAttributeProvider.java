package dev.hspl.hspl2shop.common.component;

import org.jspecify.annotations.NullMarked;

// impl: StaticApplicationAttributeProvider,
// ConfigFileApplicationAttributeProvider, DatabaseApplicationAttributeProvider
// it provides attribute/property/custom-config for domain and application

@NullMarked
public interface ApplicationAttributeProvider {
    short ruleUserMaxAddressAllowed(); // maximum number of address a user can register

    short delayLimitBetweenVerificationSessions(); // seconds

    short verificationSessionLifetime(); // seconds

    short refreshTokenLifetimeHours(); // hours

    String jwtSignatureSecret();

    short accessTokenLifetimeMinutes();

    short opaqueTokenRandomBytesCount();
}
