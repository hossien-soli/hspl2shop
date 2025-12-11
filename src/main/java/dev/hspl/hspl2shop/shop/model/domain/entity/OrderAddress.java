package dev.hspl.hspl2shop.shop.model.domain.entity;

import dev.hspl.hspl2shop.common.value.FullName;
import dev.hspl.hspl2shop.common.value.PhoneNumber;
import dev.hspl.hspl2shop.shop.value.LiteralFullAddress;
import dev.hspl.hspl2shop.shop.value.PostalCode;
import lombok.Getter;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.UUID;

@Getter
@NullMarked
public class OrderAddress {
    private UUID orderId; // this is also the primary key

    private FullName deliveryFullName; // delivery fullName(default=user's fullName)

    private PhoneNumber deliveryPhoneNumber; // delivery phoneNumber(default=user's phoneNumber)

    @Nullable
    private PhoneNumber secondaryPhoneNumber; // optional

    private short cityId;

    private LiteralFullAddress literalFullAddress;

    private PostalCode postalCode;
}
