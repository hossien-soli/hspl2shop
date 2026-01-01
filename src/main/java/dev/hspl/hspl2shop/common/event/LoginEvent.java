package dev.hspl.hspl2shop.common.event;

import dev.hspl.hspl2shop.common.DomainEvent;
import dev.hspl.hspl2shop.common.value.FullName;
import dev.hspl.hspl2shop.common.value.PhoneNumber;
import dev.hspl.hspl2shop.common.value.UserRole;
import dev.hspl.hspl2shop.user.model.write.entity.User;
import org.jspecify.annotations.NullMarked;

import java.util.UUID;

@NullMarked
public record LoginEvent(
        UserRole userRole,
        UUID userId,
        UUID loginSessionId,
        FullName userFullName,
        PhoneNumber userPhoneNumber
) implements DomainEvent {
    @Override
    public String relatedEntityName() {
        return User.class.getSimpleName();
    }

    @Override
    public String relatedEntityIdAsString() {
        return userId.toString();
    }
}
