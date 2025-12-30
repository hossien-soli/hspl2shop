package dev.hspl.hspl2shop.user.model.impl;

import dev.hspl.hspl2shop.common.exception.EntityVersionMismatchException;
import dev.hspl.hspl2shop.user.model.impl.jpa.repository.LoginSessionJpaRepository;
import dev.hspl.hspl2shop.user.model.impl.jpa.repository.RefreshTokenJpaRepository;
import dev.hspl.hspl2shop.user.model.write.entity.RefreshToken;
import dev.hspl.hspl2shop.user.model.write.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NullMarked;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
@NullMarked
public class SqlJpaRefreshTokenRepository implements RefreshTokenRepository {
    private final LoginSessionJpaRepository loginSessionJpaRepository;
    private final RefreshTokenJpaRepository refreshTokenJpaRepository;

    @Override
    public Optional<RefreshToken> find(UUID id) {
        return Optional.empty();
    }

    @Override
    public void save(RefreshToken refreshToken) throws EntityVersionMismatchException {

    }
}
