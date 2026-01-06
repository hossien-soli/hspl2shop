package dev.hspl.hspl2shop.shop.web;

import dev.hspl.hspl2shop.shop.model.read.dto.ShopCategoryDetailDto;
import dev.hspl.hspl2shop.shop.model.read.dto.ShopCategoryDto;
import dev.hspl.hspl2shop.shop.service.read.ShopQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ShopController {
    private final ShopQueryService queryService;

    @GetMapping("/shop/category")
    @ResponseStatus(HttpStatus.OK)
    public List<ShopCategoryDto> fetchAllCategories() {
        return queryService.fetchAllCategories();
    }

    @GetMapping("/shop/category-detail")
    @ResponseStatus(HttpStatus.OK)
    public List<ShopCategoryDetailDto> fetchAllCategoryDetails() {
        return queryService.fetchAllCategoryDetails();
    }
}
