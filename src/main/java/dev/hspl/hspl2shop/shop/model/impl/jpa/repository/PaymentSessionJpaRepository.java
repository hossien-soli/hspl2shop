package dev.hspl.hspl2shop.shop.model.impl.jpa.repository;

import dev.hspl.hspl2shop.shop.model.impl.jpa.entity.PaymentSessionJpaEntity;
import org.jspecify.annotations.NullMarked;
import org.springframework.data.domain.Limit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@NullMarked
public interface PaymentSessionJpaRepository extends JpaRepository<PaymentSessionJpaEntity, UUID> {
    @Query("SELECT ps.createdAt FROM PaymentSession ps WHERE ps.userId = :userId ORDER BY ps.createdAt DESC")
    List<LocalDateTime> findLastSessionsByUserId(
            @Param("userId") UUID userId,
            Limit limit
    );
}
