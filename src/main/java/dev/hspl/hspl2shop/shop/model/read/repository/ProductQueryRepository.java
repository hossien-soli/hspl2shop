package dev.hspl.hspl2shop.shop.model.read.repository;

import dev.hspl.hspl2shop.shop.model.read.dto.ProductDto;
import dev.hspl.hspl2shop.shop.value.HumanReadableId;
import org.jspecify.annotations.NullMarked;

// returns products for users/customers to order

@NullMarked
public interface ProductQueryRepository {
    PageResult<ProductDto> queryAll(short pageNumber, short countInPage);

    PageResult<ProductDto> queryByCategory(HumanReadableId categoryId, short pageNumber, short countInPage);

    // queryByIds
}
