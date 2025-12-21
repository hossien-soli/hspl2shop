package dev.hspl.hspl2shop.user.model.impl;

import dev.hspl.hspl2shop.user.model.impl.jpa.repository.CityJpaRepository;
import dev.hspl.hspl2shop.user.model.impl.jpa.repository.ProvinceJpaRepository;
import dev.hspl.hspl2shop.user.model.read.dto.ProvinceCityDto;
import dev.hspl.hspl2shop.user.model.read.dto.CityDto;
import dev.hspl.hspl2shop.user.model.read.dto.ProvinceDto;
import dev.hspl.hspl2shop.user.model.read.repository.ProvinceCityQueryRepository;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NullMarked;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
@NullMarked
public class SqlJpaProvinceCityQueryRepository implements ProvinceCityQueryRepository {
    private final ProvinceJpaRepository provinceJpaRepository;
    private final CityJpaRepository cityJpaRepository;

    @Override
    public List<ProvinceCityDto> queryAll() {
        return cityJpaRepository.findAllWithProvinceDto();
    }

    @Override
    public List<ProvinceDto> queryAllProvinces() {
        return provinceJpaRepository.findAllDto();
    }

    @Override
    public List<CityDto> queryCitiesByProvinceId(short provinceId) {
        return cityJpaRepository.findAllDtoByProvinceId(provinceId);
    }
}
