package dev.hspl.hspl2shop.user.model.read.repository;

import dev.hspl.hspl2shop.common.model.UserAddressDto;
import org.jspecify.annotations.NullMarked;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@NullMarked
public interface UserAddressQueryRepository {
    Optional<UserAddressDto> queryById(UUID userId, UUID addressId);

    List<UserAddressDto> queryAll(UUID userId);
}
