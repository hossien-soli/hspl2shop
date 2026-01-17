package dev.hspl.hspl2shop.shop.exception.order;

import dev.hspl.hspl2shop.common.exception.ApplicationException;
import lombok.Getter;
import org.jspecify.annotations.NullMarked;

@Getter
@NullMarked
public class DeliveryMethodWeightLimitOrderException extends ApplicationException {
    private final short methodId;
    private final short orderTotalItems;
    private final long orderTotalWeightG;
    private final short maxWeightPossibleKG; // delivery method max weight possible

    public DeliveryMethodWeightLimitOrderException(
            short methodId, short orderTotalItems,
            long orderTotalWeightG, short maxWeightPossibleKG
    ) {
        super("[ORDER] user is trying to create an order with weight[%d g] more than selected delivery method[%d] limitation[%d kg] (total items in order is [%d])".formatted(
                orderTotalWeightG,
                methodId,
                maxWeightPossibleKG,
                orderTotalItems
        ));

        this.methodId = methodId;
        this.orderTotalItems = orderTotalItems;
        this.orderTotalWeightG = orderTotalWeightG;
        this.maxWeightPossibleKG = maxWeightPossibleKG;
    }

    @Override
    public String problemKey() {
        return "order.delivery_method_weight.limitation";
    }

    @Override
    public short statusCode() {
        return 400;
    }
}
