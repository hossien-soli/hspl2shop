package dev.hspl.hspl2shop.shop.model.write.repository;

import dev.hspl.hspl2shop.common.exception.EntityVersionMismatchException;
import dev.hspl.hspl2shop.shop.model.write.entity.PaymentSession;
import org.jspecify.annotations.NullMarked;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@NullMarked
public interface PaymentSessionRepository {
    Optional<PaymentSession> find(UUID id);

    // TODO: optimize this using postgres include(column) indexes
    Optional<LocalDateTime> findUserLastSessionCreatedAt(UUID userId);

    void save(PaymentSession session) throws EntityVersionMismatchException;
}
