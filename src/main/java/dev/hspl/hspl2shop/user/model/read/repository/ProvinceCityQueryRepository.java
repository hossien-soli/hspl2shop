package dev.hspl.hspl2shop.user.model.read.repository;

import dev.hspl.hspl2shop.user.model.read.dto.ProvinceCityDto;
import dev.hspl.hspl2shop.user.model.read.dto.CityDto;
import dev.hspl.hspl2shop.user.model.read.dto.ProvinceDto;
import org.jspecify.annotations.NullMarked;

import java.util.List;

@NullMarked
public interface ProvinceCityQueryRepository {
    List<ProvinceCityDto> queryAll();

    List<ProvinceDto> queryAllProvinces();

    List<CityDto> queryCitiesByProvinceId(short provinceId);
}
