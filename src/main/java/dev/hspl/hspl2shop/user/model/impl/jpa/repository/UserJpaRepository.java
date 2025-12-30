package dev.hspl.hspl2shop.user.model.impl.jpa.repository;

import dev.hspl.hspl2shop.user.model.impl.jpa.entity.UserJpaEntity;
import org.jspecify.annotations.NullMarked;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

@NullMarked
public interface UserJpaRepository extends JpaRepository<UserJpaEntity,UUID> {
    @Query("SELECT u FROM User u WHERE u.phoneNumber = :phoneNumber")
    Optional<UserJpaEntity> findByPhoneNumber(@Param("phoneNumber") String phoneNumber);

    boolean existsByPhoneNumber(String phoneNumber);
}
