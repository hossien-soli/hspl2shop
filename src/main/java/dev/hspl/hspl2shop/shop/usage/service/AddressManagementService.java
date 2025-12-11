package dev.hspl.hspl2shop.shop.usage.service;

import dev.hspl.hspl2shop.common.DomainUser;
import dev.hspl.hspl2shop.shop.usage.AddressRegistrationUseCase;
import dev.hspl.hspl2shop.shop.usage.command.AddressRegistrationCommand;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NullMarked;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@NullMarked
public class AddressManagementService implements AddressRegistrationUseCase {

    @Override
    public void execute(DomainUser user, AddressRegistrationCommand command) {

    }
}
