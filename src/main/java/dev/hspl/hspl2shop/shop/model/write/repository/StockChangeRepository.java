package dev.hspl.hspl2shop.shop.model.write.repository;

import dev.hspl.hspl2shop.common.exception.EntityVersionMismatchException;
import dev.hspl.hspl2shop.shop.model.write.entity.StockChange;
import org.jspecify.annotations.NullMarked;

import java.util.Optional;
import java.util.UUID;

@NullMarked
public interface StockChangeRepository {
    Optional<StockChange> find(UUID id);

    void save(StockChange change) throws EntityVersionMismatchException;
}
