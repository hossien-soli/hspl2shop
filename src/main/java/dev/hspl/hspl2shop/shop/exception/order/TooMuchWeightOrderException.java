package dev.hspl.hspl2shop.shop.exception.order;

import dev.hspl.hspl2shop.common.exception.ApplicationException;
import lombok.Getter;
import org.jspecify.annotations.NullMarked;

@Getter
@NullMarked
public class TooMuchWeightOrderException extends ApplicationException {
    private final short orderTotalItems;
    private final long orderTotalWeightG;
    private final short orderMaxWeightKGLimit;

    public TooMuchWeightOrderException(short orderTotalItems, long orderTotalWeightG, short orderMaxWeightKGLimit) {
        super("[ORDER] user is trying to create an order with weight[%d g] more than limitation[%d kg] (total items in order is [%d])".formatted(
                orderTotalWeightG,
                orderMaxWeightKGLimit,
                orderTotalItems
        ));

        this.orderTotalItems = orderTotalItems;
        this.orderTotalWeightG = orderTotalWeightG;
        this.orderMaxWeightKGLimit = orderMaxWeightKGLimit;
    }

    @Override
    public String problemKey() {
        return "order.total_weight.limitation";
    }

    @Override
    public short statusCode() {
        return 400;
    }
}
