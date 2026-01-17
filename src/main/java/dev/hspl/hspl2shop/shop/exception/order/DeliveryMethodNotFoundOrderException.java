package dev.hspl.hspl2shop.shop.exception.order;

import dev.hspl.hspl2shop.common.exception.ApplicationException;
import lombok.Getter;
import org.jspecify.annotations.NullMarked;

@Getter
@NullMarked
public class DeliveryMethodNotFoundOrderException extends ApplicationException {
    private final short deliveryMethodId;

    public DeliveryMethodNotFoundOrderException(short deliveryMethodId) {
        super("[ORDER] user is trying to select delivery method with id[%d] that doesn't exist".formatted(
                deliveryMethodId
        ));

        this.deliveryMethodId = deliveryMethodId;
    }

    @Override
    public String problemKey() {
        return "order.selected_delivery_method.not_found";
    }

    @Override
    public short statusCode() {
        return 400;
    }
}
