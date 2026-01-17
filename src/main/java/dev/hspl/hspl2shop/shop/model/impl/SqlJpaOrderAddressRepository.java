package dev.hspl.hspl2shop.shop.model.impl;

import dev.hspl.hspl2shop.common.exception.EntityVersionMismatchException;
import dev.hspl.hspl2shop.shop.model.impl.jpa.entity.OrderAddressJpaEntity;
import dev.hspl.hspl2shop.shop.model.impl.jpa.repository.OrderAddressJpaRepository;
import dev.hspl.hspl2shop.shop.model.write.entity.OrderAddress;
import dev.hspl.hspl2shop.shop.model.write.repository.OrderAddressRepository;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NullMarked;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
@NullMarked
public class SqlJpaOrderAddressRepository implements OrderAddressRepository {
    private final OrderAddressJpaRepository jpaRepository;

//    @Override
//    public Optional<OrderAddressDto> queryById(UUID addressId, UUID userId) {
//        return Optional.empty();
//    }

    @Override
    public void save(OrderAddress address) throws EntityVersionMismatchException {
        try {
            jpaRepository.saveAndFlush(OrderAddressJpaEntity.builder()
                    .id(address.getId())
                    .userId(address.getUserId())
                    .deliveryFullName(address.getDeliveryFullName().value())
                    .deliveryPhoneNumber(address.getDeliveryPhoneNumber().value())
                    .secondaryPhoneNumber(address.getSecondaryPhoneNumber() != null ? address.getSecondaryPhoneNumber().value() : null)
                    //.provinceId(address.getProvinceId())
                    .provinceName(address.getProvinceName())
                    //.cityId(address.getCityId())
                    .cityName(address.getCityName())
                    .literalFullAddress(address.getLiteralFullAddress().value())
                    .postalCode(address.getPostalCode().value())
                    .locationLat(address.getMapLocation() != null ? address.getMapLocation().lLat() : null)
                    .locationLong(address.getMapLocation() != null ? address.getMapLocation().lLong() : null)
                    .version(address.getVersion())
                    .build());
        } catch (OptimisticLockingFailureException exception) {
            throw new EntityVersionMismatchException(OrderAddress.class.getSimpleName(), address.getId().toString());
        }
    }
}
