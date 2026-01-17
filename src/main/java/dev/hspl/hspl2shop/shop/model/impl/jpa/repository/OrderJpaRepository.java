package dev.hspl.hspl2shop.shop.model.impl.jpa.repository;

import dev.hspl.hspl2shop.shop.model.impl.jpa.entity.OrderJpaEntity;
import org.jspecify.annotations.NullMarked;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

@NullMarked
public interface OrderJpaRepository extends JpaRepository<OrderJpaEntity, UUID> {
    @Query("SELECT o FROM Order o JOIN FETCH o.items WHERE o.id = :id")
    Optional<OrderJpaEntity> findByIdWithItems(@Param("id") UUID id);
}
