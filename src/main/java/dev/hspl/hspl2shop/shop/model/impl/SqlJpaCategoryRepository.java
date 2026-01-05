package dev.hspl.hspl2shop.shop.model.impl;

import dev.hspl.hspl2shop.shop.model.impl.jpa.repository.CategoryDetailJpaRepository;
import dev.hspl.hspl2shop.shop.model.impl.jpa.repository.CategoryJpaRepository;
import dev.hspl.hspl2shop.shop.model.read.dto.CategoryDetailDto;
import dev.hspl.hspl2shop.shop.model.read.dto.CategoryDto;
import dev.hspl.hspl2shop.shop.model.read.repository.CategoryQueryRepository;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NullMarked;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
@NullMarked
public class SqlJpaCategoryRepository implements CategoryQueryRepository {
    private final CategoryJpaRepository jpaRepository;
    private final CategoryDetailJpaRepository detailJpaRepository;

    @Override
    public List<CategoryDto> queryAll() {
        return jpaRepository.findAllDto();
    }

    @Override
    public List<CategoryDetailDto> queryAllDetail() {
        return detailJpaRepository.findAllDtoOrderBySortingValueAsc();
    }
}
