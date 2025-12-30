package dev.hspl.hspl2shop.user.model.impl.jpa.repository;

import dev.hspl.hspl2shop.user.model.impl.jpa.entity.RefreshTokenJpaEntity;
import org.jspecify.annotations.NullMarked;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

@NullMarked
public interface RefreshTokenJpaRepository extends JpaRepository<RefreshTokenJpaEntity, UUID> {

}
