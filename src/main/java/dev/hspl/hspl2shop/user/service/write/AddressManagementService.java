package dev.hspl.hspl2shop.user.service.write;

import dev.hspl.hspl2shop.common.ApplicationUser;
import dev.hspl.hspl2shop.common.component.ApplicationUuidGenerator;
import dev.hspl.hspl2shop.common.component.ApplicationAttributeProvider;
import dev.hspl.hspl2shop.common.exception.AccountStateException;
import dev.hspl.hspl2shop.common.exception.ClientSideEntityVersionMismatchException;
import dev.hspl.hspl2shop.common.value.*;
import dev.hspl.hspl2shop.user.exception.AddressNotFoundException;
import dev.hspl.hspl2shop.user.exception.AddressRegistrationLimitationException;
import dev.hspl.hspl2shop.user.model.write.entity.UserAddress;
import dev.hspl.hspl2shop.user.model.write.repository.UserAddressRepository;
import dev.hspl.hspl2shop.user.service.dto.AddressInfoDto;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NullMarked;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@NullMarked
@Transactional
public class AddressManagementService {
    private final ApplicationUuidGenerator uuidGenerator;
    private final UserAddressRepository addressRepository;
    private final ApplicationAttributeProvider attributeProvider;

    public UUID registerNewAddress(ApplicationUser user, AddressInfoDto addressInfo) {
        if (!user.isAccountActive()) {
            throw new AccountStateException(UserAction.REGISTER_NEW_ADDRESS);
        }

        short userAddressCount = addressRepository.countByUser(user.id());
        if (userAddressCount >= attributeProvider.ruleUserMaxAddressAllowed()) {
            throw new AddressRegistrationLimitationException();
        }

        UUID newAddressId = uuidGenerator.generateNew();
        MapLocation mapLocation = null;
        if (addressInfo.locationLat() != null && addressInfo.locationLong() != null) {
            mapLocation = new MapLocation(addressInfo.locationLat(), addressInfo.locationLong());
        }

        UserAddress newAddress = UserAddress.newAddress(
                newAddressId, user.id(), new FullName(addressInfo.deliveryFullName()),
                new PhoneNumber(addressInfo.deliveryPhoneNumber()),
                addressInfo.secondaryPhoneNumber() != null ? new PhoneNumber(addressInfo.secondaryPhoneNumber()) : null,
                addressInfo.cityId(), new LiteralFullAddress(addressInfo.literalFullAddress()),
                new PostalCode(addressInfo.postalCode()), mapLocation, LocalDateTime.now()
        );

        addressRepository.save(newAddress);

        return newAddressId;
    }

    public void editAddress(
            ApplicationUser user, UUID addressId,
            short clientSideVersion, AddressInfoDto addressInfo
    ) {
        if (!user.isAccountActive()) {
            throw new AccountStateException(UserAction.EDIT_ADDRESS);
        }

        UserAddress address = addressRepository.find(addressId).orElseThrow(AddressNotFoundException::new);

        if (!address.getUserId().equals(user.id())) {
            throw new AddressNotFoundException();
        }

        if (address.getVersion() == null || address.getVersion() != clientSideVersion) {
            throw new ClientSideEntityVersionMismatchException(UserAddress.class.getSimpleName(), addressId.toString());
        }

        MapLocation mapLocation = null;
        if (addressInfo.locationLat() != null && addressInfo.locationLong() != null) {
            mapLocation = new MapLocation(addressInfo.locationLat(), addressInfo.locationLong());
        }

        address.editAddress(
                new FullName(addressInfo.deliveryFullName()),
                new PhoneNumber(addressInfo.deliveryPhoneNumber()),
                addressInfo.secondaryPhoneNumber() != null ? new PhoneNumber(addressInfo.secondaryPhoneNumber()) : null,
                addressInfo.cityId(), new LiteralFullAddress(addressInfo.literalFullAddress()),
                new PostalCode(addressInfo.postalCode()), mapLocation, LocalDateTime.now()
        );

        addressRepository.save(address);
    }

    public void deleteAddress(ApplicationUser user, short clientSideVersion, UUID addressId) {
        if (!user.isAccountActive()) {
            throw new AccountStateException(UserAction.DELETE_ADDRESS);
        }

        UserAddress address = addressRepository.find(addressId).orElseThrow(AddressNotFoundException::new);

        if (!address.getUserId().equals(user.id())) {
            throw new AddressNotFoundException();
        }

        if (address.getVersion() == null || address.getVersion() != clientSideVersion) {
            throw new ClientSideEntityVersionMismatchException(UserAddress.class.getSimpleName(), addressId.toString());
        }

        addressRepository.delete(address);
    }
}
