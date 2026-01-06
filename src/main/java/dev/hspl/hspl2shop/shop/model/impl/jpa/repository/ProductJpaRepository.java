package dev.hspl.hspl2shop.shop.model.impl.jpa.repository;

import dev.hspl.hspl2shop.shop.model.impl.jpa.entity.ProductJpaEntity;
import dev.hspl.hspl2shop.shop.model.read.dto.ShopProductDto;
import org.jspecify.annotations.NullMarked;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

@NullMarked
public interface ProductJpaRepository extends JpaRepository<ProductJpaEntity, String> {
    @Query("""
            SELECT new dev.hspl.hspl2shop.shop.model.read.dto.ShopProductDto(\
            ) \
            FROM Product p WHERE p.visible = TRUE ORDER BY p.sortingValue ASC""")
    Page<ShopProductDto> findAllShopDto(Pageable pageable);
}
