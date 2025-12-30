package dev.hspl.hspl2shop.user.model.impl.jpa.repository;

import dev.hspl.hspl2shop.user.model.impl.jpa.entity.LoginSessionJpaEntity;
import org.jspecify.annotations.NullMarked;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

@NullMarked
public interface LoginSessionJpaRepository extends JpaRepository<LoginSessionJpaEntity, UUID> {

}
