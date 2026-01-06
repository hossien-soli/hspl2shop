package dev.hspl.hspl2shop.shop.model.impl;

import dev.hspl.hspl2shop.common.value.PageResult;
import dev.hspl.hspl2shop.shop.model.impl.jpa.repository.ProductJpaRepository;
import dev.hspl.hspl2shop.shop.model.read.dto.ShopProductDto;
import dev.hspl.hspl2shop.shop.model.read.repository.ProductQueryRepository;
import dev.hspl.hspl2shop.shop.model.write.repository.ProductForOrderRepository;
import dev.hspl.hspl2shop.shop.model.write.repository.ProductRepository;
import dev.hspl.hspl2shop.shop.value.HumanReadableId;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NullMarked;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
@NullMarked
public class SqlJpaProductRepository implements ProductRepository, ProductQueryRepository, ProductForOrderRepository {
    private final ProductJpaRepository jpaRepository;

    @Override
    public PageResult<ShopProductDto> queryAllShop(short pageNumber, short countInPage) {
        return null;
    }

    @Override
    public PageResult<ShopProductDto> queryByCategoryShop(HumanReadableId categoryId, short pageNumber, short countInPage) {
        return null;
    }

    @Override
    public PageResult<ShopProductDto> queryDiscountedShop(short pageNumber, short countInPage) {
        return null;
    }

    @Override
    public PageResult<ShopProductDto> queryByIdListShop(List<HumanReadableId> idList, short pageNumber, short countInPage) {
        return null;
    }
}
