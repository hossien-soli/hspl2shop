package dev.hspl.hspl2shop.user.model.impl.jpa.repository;

import dev.hspl.hspl2shop.common.model.UserAddressDto;
import dev.hspl.hspl2shop.user.model.impl.jpa.entity.UserAddressJpaEntity;
import org.jspecify.annotations.NullMarked;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@NullMarked
public interface UserAddressJpaRepository extends JpaRepository<UserAddressJpaEntity, UUID> {
    short countAllByUserId(UUID userId);

    @Query("""
            SELECT \
            new dev.hspl.hspl2shop.common.model.\
            UserAddressDto(ua.id, ua.deliveryFullName, ua.deliveryPhoneNumber, ua.secondaryPhoneNumber, \
            p.id, c.id, p.name, c.name, ua.literalFullAddress, ua.postalCode, ua.locationLat, ua.locationLong, \
            ua.createdAt, ua.updatedAt, ua.version) \
            FROM UserAddress ua JOIN ua.city c JOIN c.province p \
            WHERE ua.id = :addressId AND ua.userId = :userId""")
    Optional<UserAddressDto> findDtoByUserIdAndId(
            @Param("userId") UUID userId,
            @Param("addressId") UUID addressId
    );

    @Query("""
            SELECT \
            new dev.hspl.hspl2shop.common.model.\
            UserAddressDto(ua.id, ua.deliveryFullName, ua.deliveryPhoneNumber, ua.secondaryPhoneNumber, \
            p.id, c.id, p.name, c.name, ua.literalFullAddress, ua.postalCode, ua.locationLat, ua.locationLong, \
            ua.createdAt, ua.updatedAt, ua.version) \
            FROM UserAddress ua JOIN ua.city c JOIN c.province p WHERE ua.userId = :userId \
            ORDER BY ua.createdAt ASC""")
    List<UserAddressDto> findAllDtoByUserIdOrderByCreatedAtAsc(
            @Param("userId") UUID userId
    );
}
