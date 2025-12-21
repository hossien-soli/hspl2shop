package dev.hspl.hspl2shop.user.model.write.repository;

import dev.hspl.hspl2shop.common.exception.EntityVersionMismatchException;
import dev.hspl.hspl2shop.common.value.PhoneNumber;
import dev.hspl.hspl2shop.user.model.write.entity.User;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository {
    Optional<User> find(UUID id);

    boolean existsByPhoneNumber(PhoneNumber phoneNumber);

    void save(User user) throws EntityVersionMismatchException;
}
