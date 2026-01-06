package dev.hspl.hspl2shop.shop.model.impl.jpa.repository;

import dev.hspl.hspl2shop.shop.model.impl.jpa.entity.CategoryJpaEntity;
import dev.hspl.hspl2shop.shop.model.read.dto.ShopCategoryDto;
import org.jspecify.annotations.NullMarked;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

@NullMarked
public interface CategoryJpaRepository extends JpaRepository<CategoryJpaEntity, String> {
    @Query("SELECT new dev.hspl.hspl2shop.shop.model.read.dto.ShopCategoryDto(c.id, c.name) FROM Category c")
    List<ShopCategoryDto> findAllShopDto();
}
