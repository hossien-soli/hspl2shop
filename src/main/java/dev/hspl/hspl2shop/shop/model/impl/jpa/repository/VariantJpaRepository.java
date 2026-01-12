package dev.hspl.hspl2shop.shop.model.impl.jpa.repository;

import dev.hspl.hspl2shop.shop.model.impl.jpa.entity.VariantId;
import dev.hspl.hspl2shop.shop.model.impl.jpa.entity.VariantJpaEntity;
import dev.hspl.hspl2shop.shop.model.read.dto.ShopVariantDto;
import org.jspecify.annotations.NullMarked;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

@NullMarked
public interface VariantJpaRepository extends JpaRepository<VariantJpaEntity, VariantId> {
    @Query("""
            SELECT new dev.hspl.hspl2shop.shop.model.read.dto.ShopVariantDto(\
            v.id.variantIndex, v.variantName, v.stockItems, v.price, v.discountPercent) \
            FROM Variant v \
            WHERE v.product.id = :productId AND v.visible = TRUE \
            ORDER BY v.price ASC""")
    List<ShopVariantDto> findAllDtoForShopByProductId(@Param("productId") String productId);
}
