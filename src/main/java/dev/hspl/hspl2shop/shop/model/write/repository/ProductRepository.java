package dev.hspl.hspl2shop.shop.model.write.repository;

import dev.hspl.hspl2shop.common.exception.EntityVersionMismatchException;
import dev.hspl.hspl2shop.shop.model.write.entity.Product;
import dev.hspl.hspl2shop.shop.value.HumanReadableId;
import org.jspecify.annotations.NullMarked;

import java.util.Optional;

@NullMarked
public interface ProductRepository {
    Optional<Product> find(HumanReadableId id);

    void save(Product product) throws EntityVersionMismatchException;
}
