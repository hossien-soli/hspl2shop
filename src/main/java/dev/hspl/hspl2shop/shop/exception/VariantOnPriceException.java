package dev.hspl.hspl2shop.shop.exception;

import dev.hspl.hspl2shop.common.exception.ApplicationException;
import dev.hspl.hspl2shop.shop.value.HumanReadableId;
import lombok.Getter;
import org.jspecify.annotations.NullMarked;

@Getter
@NullMarked
public class VariantOnPriceException extends ApplicationException {
    private final HumanReadableId productId;
    private final short variantIndex;

    public VariantOnPriceException(HumanReadableId productId, short variantIndex) {
        super("variant[%d] is set on visible product[%s] and can not be deleted form product or marked as invisible".formatted(
                variantIndex,
                productId.value()
        ));

        this.productId = productId;
        this.variantIndex = variantIndex;
    }

    @Override
    public String problemKey() {
        return "admin.product.variant.on_price";
    }

    @Override
    public short statusCode() {
        return 400;
    }
}
