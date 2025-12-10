package dev.hspl.hspl2shop.common.component.impl;

import com.github.f4b6a3.uuid.UuidCreator;
import dev.hspl.hspl2shop.common.component.ApplicationUuidGenerator;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class V7ApplicationUuidGenerator implements ApplicationUuidGenerator {
    @Override
    public UUID generateNew() {
        return UuidCreator.getTimeOrderedEpoch(); // UUIDv7
    }
}
