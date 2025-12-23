package dev.hspl.hspl2shop.user.model.impl;

import dev.hspl.hspl2shop.common.exception.EntityVersionMismatchException;
import dev.hspl.hspl2shop.common.value.EmailAddress;
import dev.hspl.hspl2shop.common.value.FullName;
import dev.hspl.hspl2shop.common.value.PhoneNumber;
import dev.hspl.hspl2shop.user.model.write.entity.User;
import dev.hspl.hspl2shop.user.model.write.repository.UserRepository;
import dev.hspl.hspl2shop.user.model.impl.jpa.entity.UserJpaEntity;
import dev.hspl.hspl2shop.user.model.impl.jpa.repository.UserJpaRepository;
import dev.hspl.hspl2shop.user.value.ProtectedPassword;
import jakarta.persistence.OptimisticLockException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class SqlJpaUserRepository implements UserRepository {
    private final UserJpaRepository jpaRepository;

    @Override
    public Optional<User> find(UUID id) {
        return jpaRepository.findById(id).map(jpaEntity -> User.existingUser(
                jpaEntity.getId(), new FullName(jpaEntity.getFullName()),
                new PhoneNumber(jpaEntity.getPhoneNumber()),
                new ProtectedPassword(jpaEntity.getHashedPassword()),
                jpaEntity.getEmailAddress() != null ? new EmailAddress(jpaEntity.getEmailAddress()) : null,
                jpaEntity.getRole(), jpaEntity.isBanned(), jpaEntity.getCreatedAt(),
                jpaEntity.getUpdatedAt(), jpaEntity.getVersion()
        ));
    }

    @Override
    public boolean existsByPhoneNumber(PhoneNumber phoneNumber) {
        return ...;
    }

    @Override
    public void save(User user) throws EntityVersionMismatchException {
        try {
            jpaRepository.save(UserJpaEntity.builder()
                    .id(user.getId())
                    .fullName(user.getFullName().value())
                    .phoneNumber(user.getPhoneNumber().value())
                    .hashedPassword(user.getPassword().value())
                    .emailAddress(user.getEmailAddress() != null ? user.getEmailAddress().value() : null)
                    .role(user.getRole())
                    .banned(user.isBanned())
                    .createdAt(user.getCreatedAt())
                    .updatedAt(user.getUpdatedAt())
                    .version(user.getVersion())
                    .build());
        } catch (OptimisticLockException exception) {
            throw new EntityVersionMismatchException(User.class.getSimpleName(), user.getId().toString());
        }
    }
}
