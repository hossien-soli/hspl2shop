package dev.hspl.hspl2shop.shop.service.read;

import dev.hspl.hspl2shop.shop.model.read.dto.CategoryDetailDto;
import dev.hspl.hspl2shop.shop.model.read.dto.CategoryDto;
import dev.hspl.hspl2shop.shop.model.read.repository.CategoryQueryRepository;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NullMarked;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@NullMarked
@RequiredArgsConstructor
public class ShopQueryService {
    private final CategoryQueryRepository categoryQueryRepository;

    public List<CategoryDto> fetchAllCategories() {
        return categoryQueryRepository.queryAll();
    }

    public List<CategoryDetailDto> fetchAllCategoryDetails() {
        return categoryQueryRepository.queryAllDetail();
    }
}
