package dev.hspl.hspl2shop.user.service.read;

import dev.hspl.hspl2shop.user.exception.InvalidProvinceIdException;
import dev.hspl.hspl2shop.user.model.read.dto.ProvinceCityDto;
import dev.hspl.hspl2shop.user.model.read.dto.CityDto;
import dev.hspl.hspl2shop.user.model.read.dto.ProvinceDto;
import dev.hspl.hspl2shop.user.model.read.repository.ProvinceCityQueryRepository;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NullMarked;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@NullMarked
@RequiredArgsConstructor
public class AddressQueryService {
    private final ProvinceCityQueryRepository provinceCityQueryRepository;

    public List<ProvinceDto> fetchAllProvinces() {
        return provinceCityQueryRepository.queryAllProvinces();
    }

    public List<CityDto> fetchCitiesOfProvince(short provinceId) {
        if (provinceId <= 0)
            throw new InvalidProvinceIdException();

        return provinceCityQueryRepository.queryCitiesByProvinceId(provinceId);
    }

    public List<ProvinceCityDto> fetchAllCities() {
        return provinceCityQueryRepository.queryAll();
    }
}
