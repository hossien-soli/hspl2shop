package dev.hspl.hspl2shop.shop.model.impl.jpa.repository;

import dev.hspl.hspl2shop.shop.model.impl.jpa.entity.Category;
import dev.hspl.hspl2shop.shop.model.read.dto.CategoryDto;
import org.jspecify.annotations.NullMarked;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

@NullMarked
public interface CategoryJpaRepository extends JpaRepository<Category, String> {
    @Query("SELECT new dev.hspl.hspl2shop.shop.model.read.dto.CategoryDto(c.id, c.name) FROM Category c")
    List<CategoryDto> findAllDto();
}
