package dev.hspl.hspl2shop.user.model.impl.jpa.repository;

import dev.hspl.hspl2shop.user.model.impl.jpa.entity.VerificationSessionJpaEntity;
import org.jspecify.annotations.NullMarked;
import org.springframework.data.domain.Limit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@NullMarked
public interface VerificationSessionJpaRepository extends JpaRepository<VerificationSessionJpaEntity, UUID> {
    @Query("""
            SELECT vs.createdAt FROM VerificationSession vs WHERE vs.phoneNumber = :phoneNumber \
            OR vs.requestClientIdentifier = :requestClientId ORDER BY vs.createdAt DESC""")
    List<LocalDateTime> findLastSessionsByPhoneAndClientId(
            @Param("phoneNumber") String phoneNumber,
            @Param("requestClientId") String requestClientId,
            Limit limit
    );
}
