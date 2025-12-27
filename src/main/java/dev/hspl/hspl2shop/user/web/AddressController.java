package dev.hspl.hspl2shop.user.web;

import dev.hspl.hspl2shop.common.DomainUser;
import dev.hspl.hspl2shop.common.exception.UnsupportedAuthUserException;
import dev.hspl.hspl2shop.user.model.read.dto.CityDto;
import dev.hspl.hspl2shop.user.model.read.dto.UserAddressDto;
import dev.hspl.hspl2shop.user.model.read.dto.ProvinceCityDto;
import dev.hspl.hspl2shop.user.model.read.dto.ProvinceDto;
import dev.hspl.hspl2shop.user.service.dto.AddressInfoDto;
import dev.hspl.hspl2shop.user.service.read.AddressQueryService;
import dev.hspl.hspl2shop.user.service.write.AddressManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class AddressController {
    private final AddressQueryService queryService;
    private final AddressManagementService managementService;

    @GetMapping("/address/province")
    @ResponseStatus(HttpStatus.OK)
    public List<ProvinceDto> fetchAllProvinces() {
        return queryService.fetchAllProvinces();
    }

    @GetMapping("/address/province/{provinceId}")
    @ResponseStatus(HttpStatus.OK)
    public List<CityDto> fetchCitiesOfProvince(@PathVariable short provinceId) {
        return queryService.fetchCitiesOfProvince(provinceId);
    }

    @GetMapping("/address/city")
    @ResponseStatus(HttpStatus.OK)
    public List<ProvinceCityDto> fetchAllCities() {
        return queryService.fetchAllCities();
    }

    @GetMapping("/address")
    @ResponseStatus(HttpStatus.OK)
    public List<UserAddressDto> fetchAllAddresses(Authentication authentication) {
        if (authentication.getPrincipal() instanceof DomainUser user) {
            return queryService.fetchAllUserAddresses(user);
        } else {
            throw new UnsupportedAuthUserException(authentication);
        }
    }

    @PostMapping("/address")
    @ResponseStatus(HttpStatus.CREATED)
    public Map<String, Object> registerNewAddress(
            Authentication authentication,
            @RequestBody AddressInfoDto payload
    ) {
        if (authentication.getPrincipal() instanceof DomainUser user) {
            UUID newAddressId = managementService.registerNewAddress(user, payload);
            return Map.of("address_id", newAddressId.toString());
        } else {
            throw new UnsupportedAuthUserException(authentication);
        }
    }

    @PutMapping("/address/{addressId}")
    @ResponseStatus(HttpStatus.OK)
    public void editAddress(
            Authentication authentication,
            @PathVariable("addressId") UUID addressId,
            @RequestBody AddressInfoDto payload,
            @RequestHeader("X-Version") short clientSideVersion
    ) {
        if (authentication.getPrincipal() instanceof DomainUser user) {
            managementService.editAddress(user, addressId, clientSideVersion, payload);
        } else {
            throw new UnsupportedAuthUserException(authentication);
        }
    }

    @DeleteMapping("/address/{addressId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteAddress(
            Authentication authentication,
            @PathVariable("addressId") UUID addressId,
            @RequestHeader("X-Version") short clientSideVersion
    ) {
        if (authentication.getPrincipal() instanceof DomainUser user) {
            managementService.deleteAddress(user, clientSideVersion, addressId);
        } else {
            throw new UnsupportedAuthUserException(authentication);
        }
    }
}
