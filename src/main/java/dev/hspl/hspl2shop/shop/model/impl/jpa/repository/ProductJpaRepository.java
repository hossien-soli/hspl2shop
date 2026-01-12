package dev.hspl.hspl2shop.shop.model.impl.jpa.repository;

import dev.hspl.hspl2shop.shop.model.impl.jpa.entity.ProductJpaEntity;
import dev.hspl.hspl2shop.shop.model.read.dto.ShopProductDto;
import org.jspecify.annotations.NullMarked;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

@NullMarked
public interface ProductJpaRepository extends JpaRepository<ProductJpaEntity, String> {
    @Query("SELECT p FROM Product p LEFT JOIN FETCH p.variants v WHERE p.id = :productId")
    Optional<ProductJpaEntity> findByIdWithVariants(@Param("productId") String productId);

    // findAllForShop = queryShop = visible is true and sort by discounted desc then sortingValue asc

    @Query("""
            SELECT new dev.hspl.hspl2shop.shop.model.read.dto.ShopProductDto(\
            p.id, c.id, c.name, p.name, p.shortDescription, p.longDescriptionReference, \
            p.imageReferences, p.discounted, p.price) \
            FROM Product p JOIN p.category c \
            WHERE p.visible = TRUE ORDER BY p.discounted DESC, p.sortingValue ASC""")
    Page<ShopProductDto> findAllDtoForShop(Pageable pageable);

    @Query("""
            SELECT new dev.hspl.hspl2shop.shop.model.read.dto.ShopProductDto(\
            p.id, c.id, c.name, p.name, p.shortDescription, p.longDescriptionReference, \
            p.imageReferences, p.discounted, p.price) \
            FROM Product p JOIN p.category c \
            WHERE p.category.id = :categoryId AND \
            p.visible = TRUE ORDER BY p.discounted DESC, p.sortingValue ASC""")
    Page<ShopProductDto> findAllDtoForShopByCategoryId(
            @Param("categoryId") String categoryId,
            Pageable pageable
    );

    @Query("""
            SELECT new dev.hspl.hspl2shop.shop.model.read.dto.ShopProductDto(\
            p.id, c.id, c.name, p.name, p.shortDescription, p.longDescriptionReference, \
            p.imageReferences, p.discounted, p.price) \
            FROM Product p JOIN p.category c \
            WHERE p.discounted = TRUE AND \
            p.visible = TRUE ORDER BY p.discounted DESC, p.sortingValue ASC""")
    Page<ShopProductDto> findAllDtoForShopByDiscountedIsTrue(Pageable pageable);

    @Query("""
            SELECT new dev.hspl.hspl2shop.shop.model.read.dto.ShopProductDto(\
            p.id, c.id, c.name, p.name, p.shortDescription, p.longDescriptionReference, \
            p.imageReferences, p.discounted, p.price) \
            FROM Product p JOIN p.category c \
            WHERE p.id IN :idList AND \
            p.visible = TRUE ORDER BY p.discounted DESC, p.sortingValue ASC""")
    List<ShopProductDto> findAllDtoForShopByIdIn(
            @Param("idList") List<String> idList
    );
}
