package dev.hspl.hspl2shop.shop.model.impl;

import dev.hspl.hspl2shop.common.exception.EntityVersionMismatchException;
import dev.hspl.hspl2shop.shop.model.impl.jpa.entity.CategoryDetail;
import dev.hspl.hspl2shop.shop.model.impl.jpa.entity.CategoryJpaEntity;
import dev.hspl.hspl2shop.shop.model.impl.jpa.repository.CategoryDetailJpaRepository;
import dev.hspl.hspl2shop.shop.model.impl.jpa.repository.CategoryJpaRepository;
import dev.hspl.hspl2shop.shop.model.read.dto.CategoryDto;
import dev.hspl.hspl2shop.shop.model.read.dto.ShopCategoryDetailDto;
import dev.hspl.hspl2shop.shop.model.read.dto.ShopCategoryDto;
import dev.hspl.hspl2shop.shop.model.read.repository.CategoryQueryRepository;
import dev.hspl.hspl2shop.shop.model.write.entity.Category;
import dev.hspl.hspl2shop.shop.model.write.repository.CategoryRepository;
import dev.hspl.hspl2shop.shop.value.CategoryName;
import dev.hspl.hspl2shop.shop.value.ExternalFileReference;
import dev.hspl.hspl2shop.shop.value.HumanReadableId;
import dev.hspl.hspl2shop.shop.value.ShortDescription;
import dev.hspl.hspl2shop.user.model.write.entity.User;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NullMarked;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
@NullMarked
public class SqlJpaCategoryRepository implements CategoryRepository, CategoryQueryRepository {
    private final CategoryJpaRepository jpaRepository;
    private final CategoryDetailJpaRepository detailJpaRepository;

    @Override
    public Optional<Category> find(HumanReadableId id) {
        return detailJpaRepository.findByIdWithCategory(id.value())
                .map(detail -> Category.existingCategory(
                        new HumanReadableId(detail.getId()),
                        new CategoryName(detail.getCategory().getName()),
                        new ShortDescription(detail.getShortDescription()),
                        detail.getLongDescriptionReference() != null ?
                                new ExternalFileReference(detail.getLongDescriptionReference()) : null,
                        detail.getImageReferences() != null ? Arrays.stream(detail.getImageReferences())
                                .filter(Objects::nonNull)
                                .map(ExternalFileReference::new).collect(Collectors.toSet()) : null,
                        detail.getSortingValue(), detail.getCreatedAt(), detail.getUpdatedAt(),
                        detail.getVersion()
                ));
    }

    private CategoryDetail mapCategoryToJpaEntity(Category category) {
        CategoryJpaEntity categoryJpaEntity = CategoryJpaEntity.builder()
                .id(category.getId().value())
                .name(category.getName().value())
                .build();

        String[] imageReferences = category.getImageReferences() != null ?
                category.getImageReferences().stream().map(ExternalFileReference::value)
                        .toArray(String[]::new) : null;

        return CategoryDetail.builder()
                .id(category.getId().value())
                .category(categoryJpaEntity)
                .shortDescription(category.getShortDescription().value())
                .longDescriptionReference(category.getLongDescriptionReference() != null ? category.getLongDescriptionReference().value() : null)
                .imageReferences(imageReferences)
                .sortingValue(category.getSortingValue())
                .createdAt(category.getCreatedAt())
                .updatedAt(category.getUpdatedAt())
                .version(category.getVersion())
                .build();
    }

    @Override
    public void save(Category category) throws EntityVersionMismatchException {
        try {
            detailJpaRepository.save(mapCategoryToJpaEntity(category));
        } catch (OptimisticLockingFailureException exception) {
            throw new EntityVersionMismatchException(Category.class.getSimpleName(), category.getId().value());
        }
    }

    @Override
    public void delete(Category category) throws EntityVersionMismatchException {
        try {
            detailJpaRepository.delete(mapCategoryToJpaEntity(category));
        } catch (OptimisticLockingFailureException exception) {
            throw new EntityVersionMismatchException(Category.class.getSimpleName(), category.getId().value());
        }
    }

    @Override
    public List<CategoryDto> queryAll() {
        return detailJpaRepository.findAllDtoOrderByCreatedAtAsc();
    }

    @Override
    public List<ShopCategoryDto> queryAllShop() {
        return jpaRepository.findAllShopDto();
    }

    @Override
    public List<ShopCategoryDetailDto> queryAllDetailShop() {
        return detailJpaRepository.findAllShopDtoOrderBySortingValueAsc();
    }
}
