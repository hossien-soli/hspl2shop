package dev.hspl.hspl2shop.user.model.impl.jpa.repository;

import dev.hspl.hspl2shop.user.model.impl.jpa.entity.City;
import dev.hspl.hspl2shop.user.model.read.dto.ProvinceCityDto;
import dev.hspl.hspl2shop.user.model.read.dto.CityDto;
import org.jspecify.annotations.NullMarked;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

@NullMarked
public interface CityJpaRepository extends JpaRepository<City, Short> {
    @Query("SELECT new dev.hspl.hspl2shop.user.model.read.dto.CityDto(c.id, c.name) FROM City c WHERE c.province.id = :provinceId")
    List<CityDto> findAllDtoByProvinceId(@Param("provinceId") short provinceId);

    @Query("SELECT new dev.hspl.hspl2shop.user.model.read.dto.ProvinceCityDto(c.id, p.id, p.name, c.name) FROM City c JOIN c.province p")
    List<ProvinceCityDto> findAllWithProvinceDto();
}
