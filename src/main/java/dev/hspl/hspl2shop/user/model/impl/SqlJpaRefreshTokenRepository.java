package dev.hspl.hspl2shop.user.model.impl;

import dev.hspl.hspl2shop.common.exception.EntityVersionMismatchException;
import dev.hspl.hspl2shop.user.model.impl.jpa.entity.LoginSessionJpaEntity;
import dev.hspl.hspl2shop.user.model.impl.jpa.entity.RefreshTokenJpaEntity;
import dev.hspl.hspl2shop.user.model.impl.jpa.repository.LoginSessionJpaRepository;
import dev.hspl.hspl2shop.user.model.impl.jpa.repository.RefreshTokenJpaRepository;
import dev.hspl.hspl2shop.user.model.write.entity.LoginSession;
import dev.hspl.hspl2shop.user.model.write.entity.RefreshToken;
import dev.hspl.hspl2shop.user.model.write.repository.RefreshTokenRepository;
import dev.hspl.hspl2shop.user.value.ProtectedOpaqueToken;
import dev.hspl.hspl2shop.user.value.RequestClientIdentifier;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NullMarked;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
@NullMarked
public class SqlJpaRefreshTokenRepository implements RefreshTokenRepository {
    private final LoginSessionJpaRepository sessionJpaRepository;
    private final RefreshTokenJpaRepository tokenJpaRepository;

    @Override
    public Optional<RefreshToken> find(UUID id) {
        Optional<RefreshTokenJpaEntity> fetchResult = tokenJpaRepository.findByIdWithSession(id);
        if (fetchResult.isEmpty()) {
            return Optional.empty();
        }

        RefreshTokenJpaEntity tokenJpaEntity = fetchResult.get();
        LoginSessionJpaEntity sessionJpaEntity = tokenJpaEntity.getLoginSession();

        LoginSession loginSession = LoginSession.existingSession(
                sessionJpaEntity.getId(), sessionJpaEntity.getUserId(), sessionJpaEntity.getNumberOfTokenRefresh(),
                sessionJpaEntity.getState(), new RequestClientIdentifier(sessionJpaEntity.getRequestClientIdentifier()),
                sessionJpaEntity.getCreatedAt(), sessionJpaEntity.getStateUpdatedAt(), sessionJpaEntity.getVersion()
        );

        return Optional.of(RefreshToken.existingToken(
                tokenJpaEntity.getId(), loginSession,
                new ProtectedOpaqueToken(tokenJpaEntity.getHashedToken()),
                tokenJpaEntity.getLifetimeHours(),
                tokenJpaEntity.isRefreshed(),
                tokenJpaEntity.getCreatedAt(),
                tokenJpaEntity.getRefreshedAt(),
                tokenJpaEntity.getVersion()
        ));
    }

    @Override
    public void save(RefreshToken refreshToken) throws EntityVersionMismatchException {
        LoginSession session = refreshToken.getLoginSession();

        var sessionJpaEntity = LoginSessionJpaEntity.builder()
                .id(session.getId())
                .userId(session.getUserId())
                .numberOfTokenRefresh(session.getNumberOfTokenRefresh())
                .state(session.getState())
                .requestClientIdentifier(session.getRequestClientIdentifier().value())
                .createdAt(session.getCreatedAt())
                .stateUpdatedAt(session.getStateUpdatedAt())
                .version(session.getVersion())
                .build();

        var tokenJpaEntity = RefreshTokenJpaEntity.builder()
                .id(refreshToken.getId())
                .loginSession(sessionJpaEntity)
                .hashedToken(refreshToken.getToken().value())
                .lifetimeHours(refreshToken.getLifetimeHours())
                .refreshed(refreshToken.isRefreshed())
                .createdAt(refreshToken.getCreatedAt())
                .refreshedAt(refreshToken.getRefreshedAt())
                .version(refreshToken.getVersion())
                .build();

        try {
            sessionJpaRepository.save(sessionJpaEntity);
        } catch (OptimisticLockingFailureException exception) {
            throw new EntityVersionMismatchException(LoginSession.class.getSimpleName(), session.getId().toString());
        }

        try {
            tokenJpaRepository.save(tokenJpaEntity);
        } catch (OptimisticLockingFailureException exception) {
            throw new EntityVersionMismatchException(RefreshToken.class.getSimpleName(), refreshToken.getId().toString());
        }
    }
}
