package dev.hspl.hspl2shop.common.component.impl;

import dev.hspl.hspl2shop.common.component.DomainAttributeProvider;
import org.springframework.stereotype.Component;

// better impl: ConfigFileDomainAttributeProvider

@Component
public class StaticDomainAttributeProvider implements DomainAttributeProvider {
    @Override
    public short ruleUserMaxAddressAllowed() {
        return 10;
    }
}
