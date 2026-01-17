package dev.hspl.hspl2shop.shop.exception.order;

import dev.hspl.hspl2shop.common.exception.ApplicationException;
import lombok.Getter;
import org.jspecify.annotations.NullMarked;

@Getter
@NullMarked
public class InactiveDeliveryMethodOrderException extends ApplicationException {
    private final short deliveryMethodId;

    public InactiveDeliveryMethodOrderException(short deliveryMethodId) {
        super("[ORDER] user is trying to select delivery method[%d] but it doesn't active".formatted(
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
