package dev.hspl.hspl2shop.shop.model.impl.jpa.repository;

import dev.hspl.hspl2shop.shop.model.impl.jpa.entity.CategoryDetail;
import dev.hspl.hspl2shop.shop.model.read.dto.CategoryDto;
import dev.hspl.hspl2shop.shop.model.read.dto.ShopCategoryDetailDto;
import org.jspecify.annotations.NullMarked;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

@NullMarked
public interface CategoryDetailJpaRepository extends JpaRepository<CategoryDetail, String> {
    @Query("SELECT cd FROM CategoryDetail cd JOIN FETCH cd.category WHERE cd.id = :detailId")
    Optional<CategoryDetail> findByIdWithCategory(@Param("detailId") String detailId);

    @Query("""
            SELECT new dev.hspl.hspl2shop.shop.model.read.dto.CategoryDto(\
            cd.id, c.name, cd.shortDescription, cd.longDescriptionReference, cd.imageReferences, \
            cd.sortingValue, cd.createdAt, cd.updatedAt, cd.version) \
            FROM CategoryDetail cd JOIN cd.category c \
            ORDER BY cd.createdAt ASC""")
    List<CategoryDto> findAllDtoOrderByCreatedAtAsc();

    @Query("""
            SELECT new dev.hspl.hspl2shop.shop.model.read.dto.ShopCategoryDetailDto(\
            c.id, c.name, cd.shortDescription, cd.longDescriptionReference, cd.imageReferences) \
            FROM CategoryDetail cd JOIN cd.category c \
            ORDER BY cd.sortingValue ASC""")
    List<ShopCategoryDetailDto> findAllShopDtoOrderBySortingValueAsc();
}
