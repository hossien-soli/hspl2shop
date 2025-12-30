package dev.hspl.hspl2shop.common.component;

import dev.hspl.hspl2shop.common.DomainEvent;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class DomainEventLogger {
    private static final Logger LOGGER = LoggerFactory.getLogger(DomainEventLogger.class);

    @TransactionalEventListener(fallbackExecution = true)
    @Async
    public void listen(DomainEvent event) {
        LOGGER.info("Event[{}] Entity[{}] EntityId[{}]", event.getClass().getSimpleName(),
                event.relatedEntityName(), event.relatedEntityIdAsString());
    }
}
