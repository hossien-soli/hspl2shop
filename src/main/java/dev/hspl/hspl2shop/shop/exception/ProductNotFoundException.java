package dev.hspl.hspl2shop.shop.exception;

import dev.hspl.hspl2shop.common.exception.ApplicationException;
import dev.hspl.hspl2shop.shop.value.HumanReadableId;
import lombok.Getter;
import org.jspecify.annotations.NullMarked;

@Getter
@NullMarked
public class ProductNotFoundException extends ApplicationException {
    private final HumanReadableId productId;

    public ProductNotFoundException(HumanReadableId productId) {
        super("no product found with id[%s]".formatted(productId.value()));

        this.productId = productId;
    }

    @Override
    public String problemKey() {
        return "admin.product.entity.not_found";
    }

    @Override
    public short statusCode() {
        return 400;
    }
}
