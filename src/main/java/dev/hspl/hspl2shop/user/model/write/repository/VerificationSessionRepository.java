package dev.hspl.hspl2shop.user.model.write.repository;

import dev.hspl.hspl2shop.common.exception.EntityVersionMismatchException;
import dev.hspl.hspl2shop.user.model.write.entity.VerificationSession;

import java.util.Optional;
import java.util.UUID;

// Consider redis repository implementation if application need better performance

public interface VerificationSessionRepository {
    Optional<VerificationSession> find(UUID id);

    void save(VerificationSession session) throws EntityVersionMismatchException;
}
