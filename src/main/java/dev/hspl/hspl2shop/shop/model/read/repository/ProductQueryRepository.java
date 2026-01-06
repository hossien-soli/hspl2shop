package dev.hspl.hspl2shop.shop.model.read.repository;

import dev.hspl.hspl2shop.common.value.PageResult;
import dev.hspl.hspl2shop.shop.model.read.dto.ShopProductDto;
import dev.hspl.hspl2shop.shop.value.HumanReadableId;
import org.jspecify.annotations.NullMarked;

import java.util.List;

// returns products for users/customers to order

@NullMarked
public interface ProductQueryRepository {
    PageResult<ShopProductDto> queryAllShop(short pageNumber, short countInPage);

    PageResult<ShopProductDto> queryByCategoryShop(HumanReadableId categoryId, short pageNumber, short countInPage);

    PageResult<ShopProductDto> queryDiscountedShop(short pageNumber, short countInPage);

    PageResult<ShopProductDto> queryByIdListShop(List<HumanReadableId> idList, short pageNumber, short countInPage);
}
