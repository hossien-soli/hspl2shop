package dev.hspl.hspl2shop.user.usage.service;

import dev.hspl.hspl2shop.common.component.ApplicationUuidGenerator;
import dev.hspl.hspl2shop.user.component.PersistedValueProtector;
import dev.hspl.hspl2shop.user.model.domain.UserRepository;
import dev.hspl.hspl2shop.user.model.domain.VerificationSessionRepository;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NullMarked;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@NullMarked
@Transactional
public class UserAuthenticationService {
    private final ApplicationUuidGenerator uuidGenerator;
    private final PersistedValueProtector persistedValueProtector;
    private final VerificationSessionRepository verificationSessionRepository;
    private final UserRepository userRepository;


}
