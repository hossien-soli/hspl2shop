package dev.hspl.hspl2shop.shop.model.read.repository;

import dev.hspl.hspl2shop.shop.model.read.dto.CategoryDetailDto;
import dev.hspl.hspl2shop.shop.model.read.dto.CategoryDto;
import org.jspecify.annotations.NullMarked;

import java.util.List;

@NullMarked
public interface CategoryQueryRepository {
    List<CategoryDto> queryAll();

    List<CategoryDetailDto> queryAllDetail();
}
