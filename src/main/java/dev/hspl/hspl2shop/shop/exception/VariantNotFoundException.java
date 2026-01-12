package dev.hspl.hspl2shop.shop.exception;

import dev.hspl.hspl2shop.common.exception.ApplicationException;
import dev.hspl.hspl2shop.shop.value.HumanReadableId;
import lombok.Getter;
import org.jspecify.annotations.NullMarked;

@Getter
@NullMarked
public class VariantNotFoundException extends ApplicationException {
    private final HumanReadableId productId;
    private final short variantIndex;

    public VariantNotFoundException(HumanReadableId productId, short variantIndex) {
        super("variant not found on product[%s] with index[%d]".formatted(
                productId.value(),
                variantIndex
        ));

        this.productId = productId;
        this.variantIndex = variantIndex;
    }

    @Override
    public String problemKey() {
        return "admin.product.variant.entity.not_found";
    }

    @Override
    public short statusCode() {
        return 400;
    }
}
