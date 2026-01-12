package dev.hspl.hspl2shop.shop.exception;

import dev.hspl.hspl2shop.common.exception.ApplicationException;
import dev.hspl.hspl2shop.shop.value.HumanReadableId;
import lombok.Getter;
import org.jspecify.annotations.NullMarked;

@Getter
@NullMarked
public class ProductCanNotBeVisibleException extends ApplicationException {
    private final HumanReadableId productId;

    public ProductCanNotBeVisibleException(HumanReadableId productId) {
        super("admin is trying to mark product[%s] as visible but conditions are not met".formatted(
                productId.value()
        ));

        this.productId = productId;
    }

    @Override
    public String problemKey() {
        return "admin.product.visible.bad_state";
    }

    @Override
    public short statusCode() {
        return 400;
    }
}
