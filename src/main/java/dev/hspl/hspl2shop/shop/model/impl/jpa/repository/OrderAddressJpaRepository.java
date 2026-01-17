package dev.hspl.hspl2shop.shop.model.impl.jpa.repository;

import dev.hspl.hspl2shop.shop.model.impl.jpa.entity.OrderAddressJpaEntity;
import org.jspecify.annotations.NullMarked;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

@NullMarked
public interface OrderAddressJpaRepository extends JpaRepository<OrderAddressJpaEntity, UUID> {

}
