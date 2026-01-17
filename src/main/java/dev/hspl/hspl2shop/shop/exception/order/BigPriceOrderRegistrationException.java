package dev.hspl.hspl2shop.shop.exception.order;

import dev.hspl.hspl2shop.common.exception.ApplicationException;
import lombok.Getter;
import org.jspecify.annotations.NullMarked;

@Getter
@NullMarked
public class BigPriceOrderRegistrationException extends ApplicationException {
    private final short orderTotalItems;
    private final long orderTotalPrice;
    private final long orderMaxPriceLimit;

    public BigPriceOrderRegistrationException(short orderTotalItems, long orderTotalPrice, long orderMaxPriceLimit) {
        super("[ORDER] user is trying to create an order with total price[%d] more than limitation[%d] (total items in order is [%d])".formatted(
                orderTotalPrice,
                orderMaxPriceLimit,
                orderTotalItems
        ));

        this.orderTotalItems = orderTotalItems;
        this.orderTotalPrice = orderTotalPrice;
        this.orderMaxPriceLimit = orderMaxPriceLimit;
    }

    @Override
    public String problemKey() {
        return "order.total_price.limitation";
    }

    @Override
    public short statusCode() {
        return 400;
    }
}
