package dev.hspl.hspl2shop.shop.model.read.repository;

import dev.hspl.hspl2shop.shop.model.read.dto.ShopCategoryDetailDto;
import dev.hspl.hspl2shop.shop.model.read.dto.ShopCategoryDto;
import org.jspecify.annotations.NullMarked;

import java.util.List;

@NullMarked
public interface CategoryQueryRepository {
    List<ShopCategoryDto> queryAllShop();

    List<ShopCategoryDetailDto> queryAllDetailShop();
}
