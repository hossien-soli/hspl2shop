package dev.hspl.hspl2shop.shop.model.impl;

import dev.hspl.hspl2shop.common.exception.EntityVersionMismatchException;
import dev.hspl.hspl2shop.shop.model.domain.entity.UserAddress;
import dev.hspl.hspl2shop.shop.model.domain.repository.UserAddressRepository;
import dev.hspl.hspl2shop.shop.model.impl.jpa.address.UserAddressJpaRepository;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NullMarked;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
@NullMarked
public class SqlJpaUserAddressRepository implements UserAddressRepository {
    private final UserAddressJpaRepository jpaRepository;

    @Override
    public Optional<UserAddress> find(UUID id) {
        return Optional.empty();
    }

    @Override
    public short countByUser(UUID userId) {
        return 0;
    }

    @Override
    public void save(UserAddress address) throws EntityVersionMismatchException {

    }
}
