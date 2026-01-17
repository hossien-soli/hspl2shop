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

    short accessTokenLifetimeMinutes(); // min:10(because of delay)

    short opaqueTokenRandomBytesCount();

    short orderMaxItems(); // max item a single order can have

    short orderProductVariantMaxItems(); // max item of a single variant in order

    short orderMaxWeightKG(); // max weight a single order can have

    long orderMaxPrice(); // max price a single order can have / toman

    short orderLifetimeMinutes();

    String paymentGatewayCallbackUrl();

    short orderProductCheckDelaySeconds();

    short delayLimitBetweenPaymentSessions(); // seconds / delay for each user
}
