package dev.hspl.hspl2shop.shop.exception.order;

import dev.hspl.hspl2shop.common.exception.ApplicationException;
import lombok.Getter;
import org.jspecify.annotations.NullMarked;

import java.util.UUID;

@Getter
@NullMarked
public class AddressNotFoundOrderException extends ApplicationException {
    private final UUID userAddressId;

    public AddressNotFoundOrderException(UUID userAddressId) {
        super("[ORDER] no address with id[%s] found on user account".formatted(
                userAddressId.toString()
        ));

        this.userAddressId = userAddressId;
    }

    @Override
    public String problemKey() {
        return "order.selected_user_address.not_found";
    }

    @Override
    public short statusCode() {
        return 400;
    }
}
