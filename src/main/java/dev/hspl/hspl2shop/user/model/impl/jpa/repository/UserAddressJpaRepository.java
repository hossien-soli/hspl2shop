package dev.hspl.hspl2shop.user.model.impl.jpa.repository;

import dev.hspl.hspl2shop.user.model.impl.jpa.entity.UserAddressJpaEntity;
import dev.hspl.hspl2shop.user.model.read.dto.UserAddressDto;
import org.jspecify.annotations.NullMarked;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

@NullMarked
public interface UserAddressJpaRepository extends JpaRepository<UserAddressJpaEntity, UUID> {
    short countAllByUserId(UUID userId);

    @Query("""
            SELECT \
            new dev.hspl.hspl2shop.user.model.read.dto.\
            UserAddressDto(ua.id, ua.deliveryFullName, ua.deliveryPhoneNumber, ua.secondaryPhoneNumber, \
            p.id, c.id, p.name, c.name, ua.literalFullAddress, ua.postalCode, ua.locationLat, ua.locationLong, \
            ua.createdAt, ua.updatedAt, ua.version) \
            FROM UserAddress ua JOIN ua.city c JOIN c.province p WHERE ua.userId = :userId \
            ORDER BY ua.createdAt ASC""")
    List<UserAddressDto> findAllDtoByUserIdOrderByCreatedAtAsc(
            @Param("userId") UUID userId
    );
}
