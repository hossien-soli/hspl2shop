package dev.hspl.hspl2shop.shop.service.read;

import dev.hspl.hspl2shop.common.value.PaginationParams;
import dev.hspl.hspl2shop.common.value.PaginationResult;
import dev.hspl.hspl2shop.shop.model.read.dto.*;
import dev.hspl.hspl2shop.shop.model.read.repository.CategoryQueryRepository;
import dev.hspl.hspl2shop.shop.model.read.repository.DeliveryMethodQueryRepository;
import dev.hspl.hspl2shop.shop.model.read.repository.ProductQueryRepository;
import dev.hspl.hspl2shop.shop.value.HumanReadableId;
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
    private final ProductQueryRepository productQueryRepository;
    private final DeliveryMethodQueryRepository deliveryMethodQueryRepository;

    public List<ShopCategoryDto> fetchAllCategories() {
        return categoryQueryRepository.queryAllShop();
    }

    public List<ShopCategoryDetailDto> fetchAllCategoryDetails() {
        return categoryQueryRepository.queryAllDetailShop();
    }

    public PaginationResult<ShopProductDto> fetchAllProducts(PaginationParams params) {
        return productQueryRepository.queryAllShop(params.pageNumber(), params.countInPage());
    }

    public PaginationResult<ShopProductDto> fetchProductsByCategory(HumanReadableId categoryId, PaginationParams params) {
        return productQueryRepository.queryByCategoryShop(categoryId, params.pageNumber(), params.countInPage());
    }

    public PaginationResult<ShopProductDto> fetchDiscountedProducts(PaginationParams params) {
        return productQueryRepository.queryDiscountedShop(params.pageNumber(), params.countInPage());
    }

    public List<ShopProductDto> fetchProductsByIdList(List<HumanReadableId> idList) {
        // TODO: add limit to list size based on order max item application attribute
        return productQueryRepository.queryByIdListShop(idList);
    }

    public List<ShopVariantDto> fetchProductVariants(HumanReadableId productId) {
        return productQueryRepository.queryProductVariants(productId);
    }

    public List<ShopDeliveryMethodDto> fetchAllDeliveryMethods() {
        return deliveryMethodQueryRepository.queryAllShop();
    }
}
