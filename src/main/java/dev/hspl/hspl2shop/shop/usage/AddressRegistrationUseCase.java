package dev.hspl.hspl2shop.shop.usage;

import dev.hspl.hspl2shop.common.DomainUser;
import dev.hspl.hspl2shop.shop.usage.command.AddressRegistrationCommand;
import org.jspecify.annotations.NullMarked;

@NullMarked
public interface AddressRegistrationUseCase {
    void execute(DomainUser user, AddressRegistrationCommand command);
}
