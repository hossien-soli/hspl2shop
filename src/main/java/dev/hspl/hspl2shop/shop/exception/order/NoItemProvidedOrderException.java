package dev.hspl.hspl2shop.shop.exception.order;

import dev.hspl.hspl2shop.common.exception.ApplicationException;

public class NoItemProvidedOrderException extends ApplicationException {
    public NoItemProvidedOrderException() {
        super("user is trying to create an order with no items provided!");
    }

    @Override
    public String problemKey() {
        return "order.items.not_provided";
    }

    @Override
    public short statusCode() {
        return 400;
    }
}
