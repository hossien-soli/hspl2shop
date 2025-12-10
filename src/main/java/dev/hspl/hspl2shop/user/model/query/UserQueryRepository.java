package dev.hspl.hspl2shop.user.model.query;

import dev.hspl.hspl2shop.common.value.PhoneNumber;

public interface UserQueryRepository {
    boolean existsByPhoneNumber(PhoneNumber phoneNumber);
}
