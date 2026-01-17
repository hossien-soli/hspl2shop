package dev.hspl.hspl2shop.shop.model.write.repository;

import dev.hspl.hspl2shop.common.exception.EntityVersionMismatchException;
import dev.hspl.hspl2shop.shop.model.write.entity.DeliveryMethod;
import dev.hspl.hspl2shop.shop.model.write.entity.DeliveryMethodForOrder;
import org.jspecify.annotations.NullMarked;

import java.util.Optional;

@NullMarked
public interface DeliveryMethodRepository {
    Optional<DeliveryMethod> find(short id);

    void save(DeliveryMethod method) throws EntityVersionMismatchException;

    Optional<DeliveryMethodForOrder> findForOrderReadOnly(short id);
}
