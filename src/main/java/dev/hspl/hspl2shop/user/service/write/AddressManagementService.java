package dev.hspl.hspl2shop.user.service.write;

import dev.hspl.hspl2shop.common.DomainUser;
import dev.hspl.hspl2shop.common.component.ApplicationUuidGenerator;
import dev.hspl.hspl2shop.user.model.write.repository.UserAddressRepository;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NullMarked;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@NullMarked
@Transactional
public class AddressManagementService {
    private final ApplicationUuidGenerator uuidGenerator;
    private final UserAddressRepository addressRepository;

    public void registerNewAddress(DomainUser user) {

    }
}
