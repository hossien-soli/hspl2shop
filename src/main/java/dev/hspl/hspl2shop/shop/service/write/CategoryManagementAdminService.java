package dev.hspl.hspl2shop.shop.service.write;

import dev.hspl.hspl2shop.common.ApplicationUser;
import dev.hspl.hspl2shop.common.exception.ClientSideEntityVersionMismatchException;
import dev.hspl.hspl2shop.common.exception.UserRoleAccessException;
import dev.hspl.hspl2shop.common.value.UserAction;
import dev.hspl.hspl2shop.common.value.UserRole;
import dev.hspl.hspl2shop.shop.exception.CategoryNotFoundException;
import dev.hspl.hspl2shop.shop.model.write.entity.Category;
import dev.hspl.hspl2shop.shop.model.write.repository.CategoryRepository;
import dev.hspl.hspl2shop.shop.service.dto.CategoryInfoDto;
import dev.hspl.hspl2shop.shop.value.CategoryName;
import dev.hspl.hspl2shop.shop.value.ExternalFileReference;
import dev.hspl.hspl2shop.shop.value.HumanReadableId;
import dev.hspl.hspl2shop.shop.value.ShortDescription;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NullMarked;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

// [Admin-Service] category management by OWNER role

@Service
@Transactional
@RequiredArgsConstructor
@NullMarked
public class CategoryManagementAdminService {
    private final CategoryRepository categoryRepository;

    public void registerNewCategory(ApplicationUser user, CategoryInfoDto info) {
        boolean hasAccess = user.role().equals(UserRole.OWNER);
        if (!hasAccess) {
            throw new UserRoleAccessException(UserAction.ADMIN_REGISTER_NEW_CATEGORY, user.role());
        }

        Set<ExternalFileReference> imageReferences = info.imageReferences() != null ?
                Arrays.stream(info.imageReferences()).filter(Objects::nonNull)
                        .map(ExternalFileReference::new)
                        .collect(Collectors.toSet()) : null;

        Category newCategory = Category.newCategory(
                new HumanReadableId(info.id()),
                new CategoryName(info.name()),
                new ShortDescription(info.shortDescription()),
                info.longDescriptionReference() != null ? new ExternalFileReference(info.longDescriptionReference()) : null,
                imageReferences,
                info.sortingValue(),
                LocalDateTime.now()
        );

        categoryRepository.save(newCategory);
    }

    public void editCategory(
            ApplicationUser user, CategoryInfoDto info,
            short clientSideVersion
    ) {
        boolean hasAccess = user.role().equals(UserRole.OWNER);
        if (!hasAccess) {
            throw new UserRoleAccessException(UserAction.ADMIN_EDIT_CATEGORY, user.role());
        }

        CategoryName newName = new CategoryName(info.name());
        ShortDescription newShortDescription = new ShortDescription(info.shortDescription());
        ExternalFileReference newLongDescriptionReference = info.longDescriptionReference() != null ?
                new ExternalFileReference(info.longDescriptionReference()) : null;

        Set<ExternalFileReference> newImageReferences = info.imageReferences() != null ?
                Arrays.stream(info.imageReferences()).filter(Objects::nonNull)
                        .map(ExternalFileReference::new)
                        .collect(Collectors.toSet()) : null;

        HumanReadableId categoryId = new HumanReadableId(info.id());

        Category category = categoryRepository.find(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException(categoryId));

        if (category.getVersion() == null || category.getVersion() != clientSideVersion) {
            throw new ClientSideEntityVersionMismatchException(Category.class.getSimpleName(), info.id());
        }

        category.editCategory(newName, newShortDescription, newLongDescriptionReference, newImageReferences,
                info.sortingValue(), LocalDateTime.now());

        categoryRepository.save(category);
    }

    public void deleteCategory(ApplicationUser user, HumanReadableId categoryId, short clientSideVersion) {
        boolean hasAccess = user.role().equals(UserRole.OWNER);
        if (!hasAccess) {
            throw new UserRoleAccessException(UserAction.ADMIN_DELETE_CATEGORY, user.role());
        }

        Category category = categoryRepository.find(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException(categoryId));

        if (category.getVersion() == null || category.getVersion() != clientSideVersion) {
            throw new ClientSideEntityVersionMismatchException(Category.class.getSimpleName(), categoryId.value());
        }

        categoryRepository.delete(category);
        // TODO: may fail if category has related products due to database constraints ON DELETE RESTRICT(handle this!)
    }
}
