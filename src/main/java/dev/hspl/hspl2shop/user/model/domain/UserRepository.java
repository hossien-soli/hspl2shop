package dev.hspl.hspl2shop.user.model.domain;

import dev.hspl.hspl2shop.common.exception.EntityVersionMismatchException;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository {
    Optional<User> find(UUID id);
    void save(User user) throws EntityVersionMismatchException;
}
