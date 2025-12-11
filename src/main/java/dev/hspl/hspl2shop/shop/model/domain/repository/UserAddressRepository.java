package dev.hspl.hspl2shop.shop.model.domain.repository;

import dev.hspl.hspl2shop.common.exception.EntityVersionMismatchException;
import dev.hspl.hspl2shop.shop.model.domain.entity.UserAddress;
import org.jspecify.annotations.NullMarked;

import java.util.Optional;
import java.util.UUID;

@NullMarked
public interface UserAddressRepository {
    Optional<UserAddress> find(UUID id);

    short countByUser(UUID userId);

    void save(UserAddress address) throws EntityVersionMismatchException;
}
