package dev.hspl.hspl2shop.shop.exception.order;

import dev.hspl.hspl2shop.common.exception.ApplicationException;
import lombok.Getter;
import org.jspecify.annotations.NullMarked;

import java.util.UUID;

@Getter
@NullMarked
public class OrderNotFoundException extends ApplicationException {
    private final UUID orderId;

    public OrderNotFoundException(UUID orderId) {
        super("[ORDER] user is trying to fetch an order with id[%s] that doesn't exists".formatted(
                orderId.toString()
        ));

        this.orderId = orderId;
    }

    @Override
    public String problemKey() {
        return "order.entity.not_found";
    }

    @Override
    public short statusCode() {
        return 400;
    }
}
