package dev.hspl.hspl2shop.common.event;

import dev.hspl.hspl2shop.common.DomainEvent;
import dev.hspl.hspl2shop.common.value.FullName;
import dev.hspl.hspl2shop.common.value.PhoneNumber;
import dev.hspl.hspl2shop.user.model.write.entity.RefreshToken;
import dev.hspl.hspl2shop.user.value.RequestClientIdentifier;

import java.util.UUID;

public record RefreshTokenReuseDetectedEvent(
        UUID refreshTokenId,
        UUID loginSessionId,
        UUID userId,
        RequestClientIdentifier requestClientIdentifier,
        FullName userFullName,
        PhoneNumber userPhoneNumber
) implements DomainEvent {
    @Override
    public String relatedEntityName() {
        return RefreshToken.class.getSimpleName();
    }

    @Override
    public String relatedEntityIdAsString() {
        return refreshTokenId.toString();
    }
}
