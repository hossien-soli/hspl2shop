package dev.hspl.hspl2shop.shop.exception;

import dev.hspl.hspl2shop.common.exception.ApplicationException;
import dev.hspl.hspl2shop.shop.value.HumanReadableId;
import lombok.Getter;
import org.jspecify.annotations.NullMarked;

@Getter
@NullMarked
public class CategoryNotFoundException extends ApplicationException {
    private final HumanReadableId categoryId;

    public CategoryNotFoundException(HumanReadableId categoryId) {
        super("no category found with id[%s]".formatted(categoryId.value()));

        this.categoryId = categoryId;
    }

    @Override
    public String problemKey() {
        return "admin.category.entity.not_found";
    }

    @Override
    public short statusCode() {
        return 400;
    }
}
