package dev.hspl.hspl2shop.user.model.read.repository;

import dev.hspl.hspl2shop.user.model.read.dto.UserAddressDto;
import org.jspecify.annotations.NullMarked;

import java.util.List;
import java.util.UUID;

@NullMarked
public interface UserAddressQueryRepository {
    List<UserAddressDto> queryAll(UUID userId);
}
