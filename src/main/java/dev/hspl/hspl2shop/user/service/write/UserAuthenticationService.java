package dev.hspl.hspl2shop.user.service.write;

import dev.hspl.hspl2shop.common.component.ApplicationUuidGenerator;
import dev.hspl.hspl2shop.common.component.DomainAttributeProvider;
import dev.hspl.hspl2shop.common.value.PhoneNumber;
import dev.hspl.hspl2shop.common.value.PlainVerificationCode;
import dev.hspl.hspl2shop.notification.NotificationModuleApi;
import dev.hspl.hspl2shop.user.component.PersistedValueProtector;
import dev.hspl.hspl2shop.user.exception.PhoneAlreadyRegisteredException;
import dev.hspl.hspl2shop.user.exception.PhoneNotRegisteredException;
import dev.hspl.hspl2shop.user.exception.PhoneVerificationLimitationException;
import dev.hspl.hspl2shop.user.model.write.entity.VerificationSession;
import dev.hspl.hspl2shop.user.model.write.repository.UserRepository;
import dev.hspl.hspl2shop.user.model.write.repository.VerificationSessionRepository;
import dev.hspl.hspl2shop.user.service.dto.CustomerRegistrationDto;
import dev.hspl.hspl2shop.user.service.dto.RequestVerificationDto;
import dev.hspl.hspl2shop.user.service.dto.VerificationRequestResult;
import dev.hspl.hspl2shop.user.value.PhoneVerificationPurpose;
import dev.hspl.hspl2shop.user.value.RequestClientIdentifier;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NullMarked;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.Duration;
import java.time.LocalDateTime;
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
    private final DomainAttributeProvider domainAttributeProvider;
    private final NotificationModuleApi notificationModuleApi;
    private final SecureRandom random;

    public VerificationRequestResult requestCustomerPhoneVerification(
            RequestClientIdentifier requestClientIdentifier, RequestVerificationDto data
    ) {
        var phoneNumber = new PhoneNumber(data.phoneNumber());
        var purpose = data.purpose();

        final LocalDateTime currentDateTime = LocalDateTime.now();
        final short delayBetweenSessions = domainAttributeProvider.delayLimitBetweenVerificationSessions();

        verificationSessionRepository.findLastSessionCreatedAt(phoneNumber, requestClientIdentifier)
                .ifPresent(createdAt -> {
                    long secondsElapsed = Math.abs(Duration.between(currentDateTime, createdAt).toSeconds());
                    if (secondsElapsed < delayBetweenSessions) {
                        throw new PhoneVerificationLimitationException(
                                phoneNumber, requestClientIdentifier, delayBetweenSessions, secondsElapsed
                        );
                    }
                });

        boolean phoneExists = userRepository.existsByPhoneNumber(phoneNumber);

        if (phoneExists && purpose.equals(PhoneVerificationPurpose.REGISTRATION)) {
            throw new PhoneAlreadyRegisteredException(phoneNumber, true);
        }

        if (!phoneExists && (purpose.equals(PhoneVerificationPurpose.PASSWORD_RESET) || purpose.equals(PhoneVerificationPurpose.PHONE_NUMBER_CHANGE))) {
            throw new PhoneNotRegisteredException(phoneNumber);
        }

        String verificationCodeRaw = String.format("%d%d%d%d", random.nextInt(1, 10),
                random.nextInt(0, 10), random.nextInt(0, 10), random.nextInt(0, 10));

        var verificationCode = new PlainVerificationCode(verificationCodeRaw);

        UUID newSessionId = uuidGenerator.generateNew();

        var session = VerificationSession.newSession(
                newSessionId, phoneNumber, persistedValueProtector.protect(verificationCode),
                requestClientIdentifier, purpose, currentDateTime
        );

        verificationSessionRepository.save(session);

        notificationModuleApi.deliverVerificationSms(phoneNumber, verificationCode);

        return new VerificationRequestResult(newSessionId, delayBetweenSessions);
    }

    public void registerNewCustomer(
            RequestClientIdentifier requestClientIdentifier, CustomerRegistrationDto data
    ) {

        // expiration
        // client identifier should match
        // code should be correct
        // check phone number unique again
        // register user
    }

    public void resetCustomerPassword(
            UUID verificationSessionId
    ) {

    }
}
