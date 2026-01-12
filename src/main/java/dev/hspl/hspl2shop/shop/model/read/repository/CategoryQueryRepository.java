package dev.hspl.hspl2shop.shop.model.read.repository;

import dev.hspl.hspl2shop.shop.model.read.dto.CategoryDto;
import dev.hspl.hspl2shop.shop.model.read.dto.ShopCategoryDetailDto;
import dev.hspl.hspl2shop.shop.model.read.dto.ShopCategoryDto;
import org.jspecify.annotations.NullMarked;

import java.util.List;

@NullMarked
public interface CategoryQueryRepository {
    List<CategoryDto> queryAll(); // only admin(OWNER/MANAGER)

    List<ShopCategoryDto> queryAllShop();

    List<ShopCategoryDetailDto> queryAllDetailShop();
}
