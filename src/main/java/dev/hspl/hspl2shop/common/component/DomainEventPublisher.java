package dev.hspl.hspl2shop.common.component;

import dev.hspl.hspl2shop.common.DomainEvent;
import org.jspecify.annotations.NullMarked;

@NullMarked
public interface DomainEventPublisher {
    void publish(DomainEvent event);
}
