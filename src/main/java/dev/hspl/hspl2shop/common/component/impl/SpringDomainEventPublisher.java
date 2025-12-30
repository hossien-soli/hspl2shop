package dev.hspl.hspl2shop.common.component.impl;

import dev.hspl.hspl2shop.common.DomainEvent;
import dev.hspl.hspl2shop.common.component.DomainEventPublisher;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NullMarked;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@NullMarked
public class SpringDomainEventPublisher implements DomainEventPublisher {
    private final ApplicationEventPublisher springEventPublisher;

    @Override
    public void publish(DomainEvent event) {
        springEventPublisher.publishEvent(event);
    }
}
