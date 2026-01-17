package dev.hspl.hspl2shop.shop.model.write.repository;

import dev.hspl.hspl2shop.common.exception.EntityVersionMismatchException;
import dev.hspl.hspl2shop.shop.model.read.dto.OrderAddressDto;
import dev.hspl.hspl2shop.shop.model.write.entity.OrderAddress;
import org.jspecify.annotations.NullMarked;

import java.util.Optional;
import java.util.UUID;

@NullMarked
public interface OrderAddressRepository {
//    Optional<OrderAddressDto> queryById(UUID addressId, UUID userId); used for getting the address of order by customer in their panel

    void save(OrderAddress address) throws EntityVersionMismatchException;
}
