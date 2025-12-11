package dev.hspl.hspl2shop.shop.model.impl.jpa.address;

import org.jspecify.annotations.NullMarked;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

@NullMarked
public interface UserAddressJpaRepository extends JpaRepository<UserAddressJpaEntity, UUID> {
}
