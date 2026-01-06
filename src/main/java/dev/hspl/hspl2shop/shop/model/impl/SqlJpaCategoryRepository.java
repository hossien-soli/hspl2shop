package dev.hspl.hspl2shop.shop.model.impl;

import dev.hspl.hspl2shop.shop.model.impl.jpa.repository.CategoryDetailJpaRepository;
import dev.hspl.hspl2shop.shop.model.impl.jpa.repository.CategoryJpaRepository;
import dev.hspl.hspl2shop.shop.model.read.dto.ShopCategoryDetailDto;
import dev.hspl.hspl2shop.shop.model.read.dto.ShopCategoryDto;
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
    public List<ShopCategoryDto> queryAllShop() {
        return jpaRepository.findAllShopDto();
    }

    @Override
    public List<ShopCategoryDetailDto> queryAllDetailShop() {
        return detailJpaRepository.findAllShopDtoOrderBySortingValueAsc();
    }
}
