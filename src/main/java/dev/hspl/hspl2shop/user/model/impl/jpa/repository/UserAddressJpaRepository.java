package dev.hspl.hspl2shop.user.model.impl.jpa.repository;

import dev.hspl.hspl2shop.user.model.impl.jpa.entity.UserAddressJpaEntity;
import org.jspecify.annotations.NullMarked;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

@NullMarked
public interface UserAddressJpaRepository extends JpaRepository<UserAddressJpaEntity, UUID> {
    short countAllByUserId(UUID userId);
}
