package dev.hspl.hspl2shop.user.service.write;

import dev.hspl.hspl2shop.common.component.ApplicationUuidGenerator;
import dev.hspl.hspl2shop.common.value.PhoneNumber;
import dev.hspl.hspl2shop.user.component.PersistedValueProtector;
import dev.hspl.hspl2shop.user.model.write.repository.UserRepository;
import dev.hspl.hspl2shop.user.model.write.repository.VerificationSessionRepository;
import dev.hspl.hspl2shop.user.value.PhoneVerificationPurpose;
import dev.hspl.hspl2shop.user.value.RequestClientIdentifier;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NullMarked;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@NullMarked
@Transactional
public class UserAuthenticationService {
    private final ApplicationUuidGenerator uuidGenerator;
    private final PersistedValueProtector persistedValueProtector;
    private final VerificationSessionRepository verificationSessionRepository;
    private final UserRepository userRepository;

    public UUID requestPhoneVerification(
            PhoneNumber phoneNumber, RequestClientIdentifier requestClientIdentifier,
            PhoneVerificationPurpose purpose
    ) {

    }

    public void registerNewUser(
            UUID verificationSessionId
    ) {

    }

    public void resetUserPassword(
            UUID verificationSessionId
    ) {

    }
}
