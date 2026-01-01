package dev.hspl.hspl2shop.user.model.impl.jpa.repository;

import dev.hspl.hspl2shop.user.model.impl.jpa.entity.RefreshTokenJpaEntity;
import org.jspecify.annotations.NullMarked;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

@NullMarked
public interface RefreshTokenJpaRepository extends JpaRepository<RefreshTokenJpaEntity, UUID> {
    @Query("SELECT rt FROM RefreshToken rt JOIN FETCH rt.loginSession WHERE rt.id = :id")
    Optional<RefreshTokenJpaEntity> findByIdWithSession(@Param("id") UUID id);
}
