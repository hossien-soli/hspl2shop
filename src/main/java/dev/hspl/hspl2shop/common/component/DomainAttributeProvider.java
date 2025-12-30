package dev.hspl.hspl2shop.common.component;

import org.jspecify.annotations.NullMarked;

// impl: StaticDomainAttributeProvider,
// ConfigFileDomainAttributeProvider, DatabaseDomainAttributeProvider

@NullMarked
public interface DomainAttributeProvider {
    short ruleUserMaxAddressAllowed(); // maximum number of address a user can register

    short delayLimitBetweenVerificationSessions(); // seconds

    short verificationSessionLifetime(); // seconds
}
