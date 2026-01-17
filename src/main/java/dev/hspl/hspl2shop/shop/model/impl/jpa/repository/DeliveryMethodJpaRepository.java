package dev.hspl.hspl2shop.shop.model.impl.jpa.repository;

import dev.hspl.hspl2shop.shop.model.impl.jpa.entity.DeliveryMethodJpaEntity;
import org.jspecify.annotations.NullMarked;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

@NullMarked
public interface DeliveryMethodJpaRepository extends JpaRepository<DeliveryMethodJpaEntity, Short> {
    @Query("SELECT dm FROM DeliveryMethod dm WHERE dm.id = :id")
    Optional<DeliveryMethodJpaEntity> findByIdReadOnly(@Param("id") short id);
    // TODO: add read-only query hint!!!!
}
