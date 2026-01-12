package dev.hspl.hspl2shop.shop.web;

import dev.hspl.hspl2shop.common.value.PaginationParams;
import dev.hspl.hspl2shop.common.value.PaginationResult;
import dev.hspl.hspl2shop.shop.model.read.dto.ShopCategoryDetailDto;
import dev.hspl.hspl2shop.shop.model.read.dto.ShopCategoryDto;
import dev.hspl.hspl2shop.shop.model.read.dto.ShopProductDto;
import dev.hspl.hspl2shop.shop.model.read.dto.ShopVariantDto;
import dev.hspl.hspl2shop.shop.service.read.ShopQueryService;
import dev.hspl.hspl2shop.shop.value.HumanReadableId;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ShopController {
    private final ShopQueryService queryService;

    @GetMapping("/shop/categories")
    @ResponseStatus(HttpStatus.OK)
    public List<ShopCategoryDto> fetchAllCategories() {
        return queryService.fetchAllCategories();
    }

    @GetMapping("/shop/category-details")
    @ResponseStatus(HttpStatus.OK)
    public List<ShopCategoryDetailDto> fetchAllCategoryDetails() {
        return queryService.fetchAllCategoryDetails();
    }

    @GetMapping("/shop/all-products")
    @ResponseStatus(HttpStatus.OK)
    public PaginationResult<ShopProductDto> fetchAllProducts(
            @RequestParam(value = "pageNumber", defaultValue = "1") short pageNumber,
            @RequestParam(value = "countInPage", defaultValue = "20") short countInPage
    ) {
        return queryService.fetchAllProducts(new PaginationParams(
                pageNumber, countInPage
        ));
    }

    @GetMapping("/shop/product-variants/{productId}")
    @ResponseStatus(HttpStatus.OK)
    public List<ShopVariantDto> fetchProductVariants(
            @PathVariable("productId") String productId
    ) {
        return queryService.fetchProductVariants(new HumanReadableId(productId));
    }

    @GetMapping("/shop/product-filter/discounted")
    @ResponseStatus(HttpStatus.OK)
    public PaginationResult<ShopProductDto> fetchDiscountedProducts(
            @RequestParam(value = "pageNumber", defaultValue = "1") short pageNumber,
            @RequestParam(value = "countInPage", defaultValue = "20") short countInPage
    ) {
        return queryService.fetchDiscountedProducts(new PaginationParams(
                pageNumber, countInPage
        ));
    }

    @GetMapping("/shop/product-filter/by-category")
    @ResponseStatus(HttpStatus.OK)
    public PaginationResult<ShopProductDto> fetchProductsByCategory(
            @RequestParam(value = "pageNumber", defaultValue = "1") short pageNumber,
            @RequestParam(value = "countInPage", defaultValue = "20") short countInPage,
            @RequestParam("categoryId") String categoryId
    ) {
        return queryService.fetchProductsByCategory(
                new HumanReadableId(categoryId),
                new PaginationParams(pageNumber, countInPage)
        );
    }

    @GetMapping("/shop/product-filter/by-id-list")
    @ResponseStatus(HttpStatus.OK)
    public List<ShopProductDto> fetchProductsByIdList(
            @RequestParam("idList") List<String> idList
    ) {
        return queryService.fetchProductsByIdList(idList.stream().map(HumanReadableId::new).toList());
    }
}
