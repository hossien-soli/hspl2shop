package dev.hspl.hspl2shop.user.model.impl;

import dev.hspl.hspl2shop.common.exception.EntityVersionMismatchException;
import dev.hspl.hspl2shop.common.value.*;
import dev.hspl.hspl2shop.user.model.impl.jpa.entity.UserAddressJpaEntity;
import dev.hspl.hspl2shop.user.model.impl.jpa.repository.CityJpaRepository;
import dev.hspl.hspl2shop.user.model.impl.jpa.repository.UserAddressJpaRepository;
import dev.hspl.hspl2shop.user.model.read.dto.UserAddressDto;
import dev.hspl.hspl2shop.user.model.read.repository.UserAddressQueryRepository;
import dev.hspl.hspl2shop.user.model.write.entity.UserAddress;
import dev.hspl.hspl2shop.user.model.write.repository.UserAddressRepository;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NullMarked;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
@NullMarked
public class SqlJpaUserAddressRepository implements UserAddressRepository, UserAddressQueryRepository {
    private final UserAddressJpaRepository jpaRepository;
    private final CityJpaRepository cityJpaRepository;

    @Override
    public Optional<UserAddress> find(UUID id) {
        return jpaRepository.findById(id).map(jpaEntity -> UserAddress.existingAddress(
                jpaEntity.getId(), jpaEntity.getUserId(),
                new FullName(jpaEntity.getDeliveryFullName()),
                new PhoneNumber(jpaEntity.getDeliveryPhoneNumber()),
                jpaEntity.getSecondaryPhoneNumber() != null ?
                        new PhoneNumber(jpaEntity.getSecondaryPhoneNumber()) : null,
                jpaEntity.getCity().getId(),
                new LiteralFullAddress(jpaEntity.getLiteralFullAddress()),
                new PostalCode(jpaEntity.getPostalCode()),
                jpaEntity.getLocationLat() != null && jpaEntity.getLocationLong() != null
                        ? new MapLocation(jpaEntity.getLocationLat(), jpaEntity.getLocationLong()) : null,
                jpaEntity.getCreatedAt(), jpaEntity.getUpdatedAt(), jpaEntity.getVersion()
        ));
    }

    @Override
    public short countByUser(UUID userId) {
        return jpaRepository.countAllByUserId(userId);
    }

    @Override
    public void save(UserAddress address) throws EntityVersionMismatchException {
        try {
            jpaRepository.save(UserAddressJpaEntity.builder()
                    .id(address.getId())
                    .userId(address.getUserId())
                    .deliveryFullName(address.getDeliveryFullName().value())
                    .deliveryPhoneNumber(address.getDeliveryPhoneNumber().value())
                    .secondaryPhoneNumber(address.getSecondaryPhoneNumber() != null ? address.getSecondaryPhoneNumber().value() : null)
                    .city(cityJpaRepository.getReferenceById(address.getCityId()))
                    .literalFullAddress(address.getLiteralFullAddress().value())
                    .postalCode(address.getPostalCode().value())
                    .locationLat(address.getMapLocation() != null ? address.getMapLocation().lLat() : null)
                    .locationLong(address.getMapLocation() != null ? address.getMapLocation().lLong() : null)
                    .createdAt(address.getCreatedAt())
                    .updatedAt(address.getUpdatedAt())
                    .version(address.getVersion())
                    .build());
        } catch (OptimisticLockingFailureException exception) {
            throw new EntityVersionMismatchException(UserAddress.class.getSimpleName(), address.getId().toString());
        }
    }

    @Override
    public void delete(UserAddress address) throws EntityVersionMismatchException {
        try {
            jpaRepository.delete(UserAddressJpaEntity.builder()
                    .id(address.getId())
                    .userId(address.getUserId())
                    .deliveryFullName(address.getDeliveryFullName().value())
                    .deliveryPhoneNumber(address.getDeliveryPhoneNumber().value())
                    .secondaryPhoneNumber(address.getSecondaryPhoneNumber() != null ? address.getSecondaryPhoneNumber().value() : null)
                    .city(cityJpaRepository.getReferenceById(address.getCityId()))
                    .literalFullAddress(address.getLiteralFullAddress().value())
                    .postalCode(address.getPostalCode().value())
                    .locationLat(address.getMapLocation() != null ? address.getMapLocation().lLat() : null)
                    .locationLong(address.getMapLocation() != null ? address.getMapLocation().lLong() : null)
                    .createdAt(address.getCreatedAt())
                    .updatedAt(address.getUpdatedAt())
                    .version(address.getVersion())
                    .build());
        } catch (OptimisticLockingFailureException exception) {
            throw new EntityVersionMismatchException(UserAddress.class.getSimpleName(), address.getId().toString());
        }
    }

    @Override
    public List<UserAddressDto> queryAll(UUID userId) {
        return jpaRepository.findAllDtoByUserIdOrderByCreatedAtAsc(userId);
    }
}
