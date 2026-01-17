package dev.hspl.hspl2shop.shop.exception.order;

import dev.hspl.hspl2shop.common.exception.ApplicationException;
import dev.hspl.hspl2shop.shop.model.write.entity.ProductForOrder;
import dev.hspl.hspl2shop.shop.value.ProductName;
import dev.hspl.hspl2shop.shop.value.ProductVariantId;
import dev.hspl.hspl2shop.shop.value.VariantName;
import lombok.Getter;
import org.jspecify.annotations.NullMarked;

@Getter
@NullMarked
public class InvisibleProductFoundOrderException extends ApplicationException {
    public InvisibleProductFoundOrderException(
            ProductVariantId productVariantId, ProductName productName,
            VariantName variantName
    ) {
        super("");

    }

    @Override
    public String problemKey() {
        return ""; // محصول فلان دیگر قابل سفارش نیست لطفا جهت پرداخت این محصول را از سفارش خود حذف کنید!
    }

    @Override
    public short statusCode() {
        return 400;
    }
}
