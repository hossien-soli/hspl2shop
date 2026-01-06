package dev.hspl.hspl2shop.shop.service.read;

import dev.hspl.hspl2shop.shop.model.read.dto.ShopCategoryDetailDto;
import dev.hspl.hspl2shop.shop.model.read.dto.ShopCategoryDto;
import dev.hspl.hspl2shop.shop.model.read.repository.CategoryQueryRepository;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NullMarked;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

// shop = customer facing ui for customer ordering/shopping interactions
// customer required info and view of data in order to interact with website to order products

@Service
@Transactional(readOnly = true)
@NullMarked
@RequiredArgsConstructor
public class ShopQueryService {
    private final CategoryQueryRepository categoryQueryRepository;

    public List<ShopCategoryDto> fetchAllCategories() {
        return categoryQueryRepository.queryAllShop();
    }

    public List<ShopCategoryDetailDto> fetchAllCategoryDetails() {
        return categoryQueryRepository.queryAllDetailShop();
    }
}
