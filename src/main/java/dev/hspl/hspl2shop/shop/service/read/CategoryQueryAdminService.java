package dev.hspl.hspl2shop.shop.service.read;

import dev.hspl.hspl2shop.common.ApplicationUser;
import dev.hspl.hspl2shop.common.exception.UserRoleAccessException;
import dev.hspl.hspl2shop.common.value.UserAction;
import dev.hspl.hspl2shop.common.value.UserRole;
import dev.hspl.hspl2shop.shop.model.read.dto.CategoryDto;
import dev.hspl.hspl2shop.shop.model.read.repository.CategoryQueryRepository;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NullMarked;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@NullMarked
public class CategoryQueryAdminService {
    private final CategoryQueryRepository queryRepository;

    public List<CategoryDto> fetchAllCategories(ApplicationUser user) {
        boolean hasAccess = user.role().equals(UserRole.OWNER) || user.role().equals(UserRole.MANAGER);
        if (!hasAccess) {
            throw new UserRoleAccessException(UserAction.ADMIN_FETCH_CATEGORIES, user.role());
        }

        return queryRepository.queryAll();
    }
}
