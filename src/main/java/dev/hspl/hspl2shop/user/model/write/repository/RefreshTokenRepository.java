package dev.hspl.hspl2shop.user.model.write.repository;

import dev.hspl.hspl2shop.common.exception.EntityVersionMismatchException;
import dev.hspl.hspl2shop.user.model.write.entity.RefreshToken;
import org.jspecify.annotations.NullMarked;

import java.util.Optional;
import java.util.UUID;

@NullMarked
public interface RefreshTokenRepository {
    Optional<RefreshToken> find(UUID id);

    void save(RefreshToken refreshToken) throws EntityVersionMismatchException;
}
