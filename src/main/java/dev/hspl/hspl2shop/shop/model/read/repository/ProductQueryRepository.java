package dev.hspl.hspl2shop.shop.model.read.repository;

import dev.hspl.hspl2shop.common.value.PaginationResult;
import dev.hspl.hspl2shop.shop.model.read.dto.ShopProductDto;
import dev.hspl.hspl2shop.shop.model.read.dto.ShopVariantDto;
import dev.hspl.hspl2shop.shop.value.HumanReadableId;
import org.jspecify.annotations.NullMarked;

import java.util.List;

// returns products for users/customers to order
// queryShop = visible is true and sort by discounted desc then sortingValue asc

@NullMarked
public interface ProductQueryRepository {
    PaginationResult<ShopProductDto> queryAllShop(short pageNumber, short countInPage);

    PaginationResult<ShopProductDto> queryByCategoryShop(HumanReadableId categoryId, short pageNumber, short countInPage);

    PaginationResult<ShopProductDto> queryDiscountedShop(short pageNumber, short countInPage);

    List<ShopProductDto> queryByIdListShop(List<HumanReadableId> idList);
    // put size limitation on idList

    List<ShopVariantDto> queryProductVariants(HumanReadableId productId);
}
