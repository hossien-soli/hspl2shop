package dev.hspl.hspl2shop.shop.model.write.repository;

import dev.hspl.hspl2shop.common.exception.EntityVersionMismatchException;
import dev.hspl.hspl2shop.shop.model.write.entity.Product;
import dev.hspl.hspl2shop.shop.model.write.entity.ProductForOrder;
import dev.hspl.hspl2shop.shop.value.HumanReadableId;
import dev.hspl.hspl2shop.shop.value.ProductVariantId;
import org.jspecify.annotations.NullMarked;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@NullMarked
public interface ProductRepository {
    Optional<Product> find(HumanReadableId id);

    void save(Product product) throws EntityVersionMismatchException;

    List<ProductForOrder> findAllForOrderByIds(Set<ProductVariantId> ids);

    void saveAllForOrder(List<ProductForOrder> products) throws EntityVersionMismatchException;
}
