package dev.hspl.hspl2shop.shop.exception;

import dev.hspl.hspl2shop.common.exception.ApplicationException;
import dev.hspl.hspl2shop.shop.value.HumanReadableId;
import lombok.Getter;
import org.jspecify.annotations.NullMarked;

@Getter
@NullMarked
public class VariantIsNotVisibleException extends ApplicationException {
    private final HumanReadableId productId;
    private final short variantIndex;

    public VariantIsNotVisibleException(HumanReadableId productId, short variantIndex) {
        super("variant[%d] on product[%s] is not visible for performing the operation".formatted(
                variantIndex,
                productId.value()
        ));

        this.productId = productId;
        this.variantIndex = variantIndex;
    }

    @Override
    public String problemKey() {
        return "admin.product.variant.invisible";
    }

    @Override
    public short statusCode() {
        return 400;
    }
}
