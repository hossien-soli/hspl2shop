package dev.hspl.hspl2shop.user.model.impl;

import dev.hspl.hspl2shop.common.exception.EntityVersionMismatchException;
import dev.hspl.hspl2shop.common.value.PhoneNumber;
import dev.hspl.hspl2shop.user.model.impl.jpa.entity.VerificationSessionJpaEntity;
import dev.hspl.hspl2shop.user.model.impl.jpa.repository.VerificationSessionJpaRepository;
import dev.hspl.hspl2shop.user.model.write.entity.VerificationSession;
import dev.hspl.hspl2shop.user.model.write.repository.VerificationSessionRepository;
import dev.hspl.hspl2shop.user.value.ProtectedVerificationCode;
import dev.hspl.hspl2shop.user.value.RequestClientIdentifier;
import jakarta.persistence.OptimisticLockException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class SqlJpaVerificationSessionRepository implements VerificationSessionRepository {
    private final VerificationSessionJpaRepository jpaRepository;

    @Override
    public Optional<VerificationSession> find(UUID id) {
        return jpaRepository.findById(id).map(jpaEntity -> VerificationSession.existingSession(
                jpaEntity.getId(), new PhoneNumber(jpaEntity.getPhoneNumber()),
                new ProtectedVerificationCode(jpaEntity.getHashedVerificationCode()),
                new RequestClientIdentifier(jpaEntity.getRequestClientIdentifier()),
                jpaEntity.getPurpose(),
                jpaEntity.isVerified(), jpaEntity.getCreatedAt(), jpaEntity.getVersion()
        ));
    }

    @Override
    public void save(VerificationSession session) throws EntityVersionMismatchException {
        try {
            jpaRepository.save(VerificationSessionJpaEntity.builder()
                    .id(session.getId())
                    .phoneNumber(session.getPhoneNumber().value())
                    .hashedVerificationCode(session.getVerificationCode().value())
                    .requestClientIdentifier(session.getRequestClientIdentifier().value())
                    .purpose(session.getPurpose())
                    .verified(session.isVerified())
                    .createdAt(session.getCreatedAt())
                    .version(session.getVersion())
                    .build());
        } catch (OptimisticLockException exception) {
            throw new EntityVersionMismatchException(VerificationSession.class.getSimpleName(), session.getId().toString());
        }
    }
}
