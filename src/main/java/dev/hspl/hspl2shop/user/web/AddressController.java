package dev.hspl.hspl2shop.user.web;

import dev.hspl.hspl2shop.user.model.read.dto.CityDto;
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

    @PostMapping("/address")
    @ResponseStatus(HttpStatus.CREATED)
    public void registerNewAddress(
            Authentication authentication,
            @RequestBody AddressInfoDto payload
    ) {

    }
}
