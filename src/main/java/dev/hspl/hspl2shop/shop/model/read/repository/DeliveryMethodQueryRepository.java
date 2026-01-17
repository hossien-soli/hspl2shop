package dev.hspl.hspl2shop.shop.model.read.repository;

import dev.hspl.hspl2shop.shop.model.read.dto.ShopDeliveryMethodDto;
import org.jspecify.annotations.NullMarked;

import java.util.List;

@NullMarked
public interface DeliveryMethodQueryRepository {
    // queryAll for admin

    List<ShopDeliveryMethodDto> queryAllShop(); // full detail and only active records
}
