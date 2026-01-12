package dev.hspl.hspl2shop.shop.model.write.repository;

import dev.hspl.hspl2shop.common.exception.EntityVersionMismatchException;
import dev.hspl.hspl2shop.shop.model.write.entity.Category;
import dev.hspl.hspl2shop.shop.value.HumanReadableId;
import org.jspecify.annotations.NullMarked;

import java.util.Optional;

@NullMarked
public interface CategoryRepository {
    Optional<Category> find(HumanReadableId id);

    void save(Category category) throws EntityVersionMismatchException;

    void delete(Category category) throws EntityVersionMismatchException;
}
