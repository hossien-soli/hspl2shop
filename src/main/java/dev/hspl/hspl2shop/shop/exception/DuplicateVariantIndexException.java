package dev.hspl.hspl2shop.shop.exception;

import dev.hspl.hspl2shop.common.exception.ApplicationException;
import lombok.Getter;
import org.jspecify.annotations.NullMarked;

@Getter
@NullMarked
public class DuplicateVariantIndexException extends ApplicationException {
    private final short variantIndex;

    public DuplicateVariantIndexException(short variantIndex) {
        super("admin is trying to add a variant to product with an variant_index value[%d] that already set on another variant".formatted(
                variantIndex
        ));

        this.variantIndex = variantIndex;
    }

    @Override
    public String problemKey() {
        return "admin.product.variant_index.duplicate";
    }

    @Override
    public short statusCode() {
        return 400;
    }
}
