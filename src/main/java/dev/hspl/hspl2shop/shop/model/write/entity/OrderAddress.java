package dev.hspl.hspl2shop.shop.model.write.entity;

import dev.hspl.hspl2shop.common.value.FullName;
import dev.hspl.hspl2shop.common.value.PhoneNumber;
import dev.hspl.hspl2shop.common.value.LiteralFullAddress;
import dev.hspl.hspl2shop.common.value.MapLocation;
import dev.hspl.hspl2shop.common.value.PostalCode;
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

    @Nullable
    private MapLocation mapLocation;
}
