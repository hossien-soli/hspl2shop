package dev.hspl.hspl2shop.shop.model.impl.jpa.repository;

import dev.hspl.hspl2shop.shop.model.impl.jpa.entity.CategoryDetail;
import dev.hspl.hspl2shop.shop.model.read.dto.ShopCategoryDetailDto;
import org.jspecify.annotations.NullMarked;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

@NullMarked
public interface CategoryDetailJpaRepository extends JpaRepository<CategoryDetail, String> {
    @Query("""
            SELECT new dev.hspl.hspl2shop.shop.model.read.dto.ShopCategoryDetailDto(\
            c.id, c.name, cd.shortDescription, cd.longDescriptionReference, cd.imageReferences) \
            FROM CategoryDetail cd JOIN cd.category c \
            ORDER BY cd.sortingValue ASC""")
    List<ShopCategoryDetailDto> findAllShopDtoOrderBySortingValueAsc();
}
