package dev.hspl.hspl2shop.shop.model.write.repository;

import dev.hspl.hspl2shop.common.exception.EntityVersionMismatchException;
import dev.hspl.hspl2shop.shop.model.write.entity.Order;
import org.jspecify.annotations.NullMarked;

import java.util.Optional;
import java.util.UUID;

@NullMarked
public interface OrderRepository {
    Optional<Order> find(UUID id);

    void save(Order order) throws EntityVersionMismatchException;
}
