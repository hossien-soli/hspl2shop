package dev.hspl.hspl2shop.user;

import dev.hspl.hspl2shop.common.model.UserAddressDto;
import dev.hspl.hspl2shop.user.model.read.repository.UserAddressQueryRepository;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NullMarked;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@NullMarked
public class UserModuleApi {
    private final UserAddressQueryRepository addressQueryRepository;

    public Optional<UserAddressDto> findUserAddressById(UUID userId, UUID addressId) {
        return addressQueryRepository.queryById(userId, addressId);
    }
}
