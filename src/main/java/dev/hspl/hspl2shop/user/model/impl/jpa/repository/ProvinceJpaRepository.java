package dev.hspl.hspl2shop.user.model.impl.jpa.repository;

import dev.hspl.hspl2shop.user.model.impl.jpa.entity.Province;
import dev.hspl.hspl2shop.user.model.read.dto.ProvinceDto;
import org.jspecify.annotations.NullMarked;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

@NullMarked
public interface ProvinceJpaRepository extends JpaRepository<Province, Short> {
    @Query("SELECT new dev.hspl.hspl2shop.user.model.read.dto.ProvinceDto(p.id, p.name) FROM Province p")
    List<ProvinceDto> findAllDto();
}
