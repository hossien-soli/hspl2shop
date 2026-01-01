package dev.hspl.hspl2shop.user.service.write;

import dev.hspl.hspl2shop.common.component.ApplicationUuidGenerator;
import dev.hspl.hspl2shop.common.component.ApplicationAttributeProvider;
import dev.hspl.hspl2shop.common.component.DomainEventPublisher;
import dev.hspl.hspl2shop.common.event.CustomerRegistrationEvent;
import dev.hspl.hspl2shop.common.event.LoginEvent;
import dev.hspl.hspl2shop.common.value.FullName;
import dev.hspl.hspl2shop.common.value.PhoneNumber;
import dev.hspl.hspl2shop.common.value.PlainVerificationCode;
import dev.hspl.hspl2shop.common.value.UserRole;
import dev.hspl.hspl2shop.notification.NotificationModuleApi;
import dev.hspl.hspl2shop.user.component.DomainUserJWTService;
import dev.hspl.hspl2shop.user.component.PersistedValueProtector;
import dev.hspl.hspl2shop.user.exception.*;
import dev.hspl.hspl2shop.user.model.write.entity.RefreshToken;
import dev.hspl.hspl2shop.user.model.write.entity.User;
import dev.hspl.hspl2shop.user.model.write.entity.VerificationSession;
import dev.hspl.hspl2shop.user.model.write.repository.RefreshTokenRepository;
import dev.hspl.hspl2shop.user.model.write.repository.UserRepository;
import dev.hspl.hspl2shop.user.model.write.repository.VerificationSessionRepository;
import dev.hspl.hspl2shop.user.service.dto.*;
import dev.hspl.hspl2shop.user.value.PhoneVerificationPurpose;
import dev.hspl.hspl2shop.user.value.PlainOpaqueToken;
import dev.hspl.hspl2shop.user.value.PlainPassword;
import dev.hspl.hspl2shop.user.value.RequestClientIdentifier;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NullMarked;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Base64;
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
    private final ApplicationAttributeProvider attributeProvider;
    private final NotificationModuleApi notificationModuleApi;
    private final SecureRandom random;
    private final DomainEventPublisher eventPublisher;
    private final RefreshTokenRepository refreshTokenRepository;
    private final DomainUserJWTService jwtService;

    public VerificationRequestResult requestCustomerPhoneVerification(
            RequestClientIdentifier requestClientIdentifier, RequestVerificationDto data
    ) {
        var phoneNumber = new PhoneNumber(data.phoneNumber());
        var purpose = data.purpose();

        final LocalDateTime currentDateTime = LocalDateTime.now();
        final short delayBetweenSessions = attributeProvider.delayLimitBetweenVerificationSessions();

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

        return new VerificationRequestResult(newSessionId, delayBetweenSessions,
                attributeProvider.verificationSessionLifetime());
    }

    public void registerNewCustomer(
            RequestClientIdentifier requestClientIdentifier, CustomerRegistrationDto data
    ) {
        FullName userFullName = new FullName(data.fullName());
        PlainPassword userPassword = new PlainPassword(data.password());

        VerificationSession session = verificationSessionRepository.find(data.verificationSessionId())
                .orElseThrow(VerificationSessionNotFoundException::new);

        LocalDateTime currentDateTime = LocalDateTime.now();
        session.checkVerifiable(PhoneVerificationPurpose.REGISTRATION, requestClientIdentifier,
                currentDateTime, attributeProvider.verificationSessionLifetime());

        if (!persistedValueProtector.matches(new PlainVerificationCode(data.verificationCode()), session.getVerificationCode())) {
            throw new IncorrectVerificationCodeException(session.getPhoneNumber());
        }

        boolean phoneExists = userRepository.existsByPhoneNumber(session.getPhoneNumber());
        if (phoneExists) {
            throw new PhoneAlreadyRegisteredException(session.getPhoneNumber(), false);
        }

        UUID newUserId = uuidGenerator.generateNew();

        User newUser = User.newUser(
                newUserId, userFullName, session.getPhoneNumber(),
                persistedValueProtector.protect(userPassword), null,
                UserRole.CUSTOMER, currentDateTime
        );

        verificationSessionRepository.save(session);
        userRepository.save(newUser);

        eventPublisher.publish(new CustomerRegistrationEvent(newUserId, userFullName, session.getPhoneNumber()));
    }

    public void resetCustomerPassword(
            RequestClientIdentifier requestClientIdentifier, PasswordResetDto data
    ) {
        PlainPassword userPassword = new PlainPassword(data.password());

        VerificationSession session = verificationSessionRepository.find(data.verificationSessionId())
                .orElseThrow(VerificationSessionNotFoundException::new);

        LocalDateTime currentDateTime = LocalDateTime.now();
        session.checkVerifiable(PhoneVerificationPurpose.PASSWORD_RESET, requestClientIdentifier,
                currentDateTime, attributeProvider.verificationSessionLifetime());

        if (!persistedValueProtector.matches(new PlainVerificationCode(data.verificationCode()), session.getVerificationCode())) {
            throw new IncorrectVerificationCodeException(session.getPhoneNumber());
        }

        User user = userRepository.findByPhoneNumber(session.getPhoneNumber())
                .orElseThrow(() -> new UserNotFoundException("password_reset_invalid_phone_number"));

        if (!user.getRole().equals(UserRole.CUSTOMER)) {
            throw new UserNotFoundException("password_reset_invalid_role");
        }

        user.updatePassword(persistedValueProtector.protect(userPassword), currentDateTime);

        userRepository.save(user);

        // TODO: invalidate all login sessions of user
    }

    public LoginResult login(
            RequestClientIdentifier requestClientIdentifier, LoginDto data
    ) {
        var phoneNumber = new PhoneNumber(data.phoneNumber());
        var password = new PlainPassword(data.password());

        User user = userRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new BadCredentialsLoginException(phoneNumber));

        if (!persistedValueProtector.matches(password, user.getPassword())) {
            throw new BadCredentialsLoginException(phoneNumber);
        }

        LocalDateTime currentDateTime = LocalDateTime.now();

        UUID newTokenId = uuidGenerator.generateNew();
        UUID newSessionId = uuidGenerator.generateNew();

        byte[] randomBytes = new byte[20];
        random.nextBytes(randomBytes);
        String rawToken = Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes);
        PlainOpaqueToken opaqueToken = new PlainOpaqueToken(rawToken);

        short lifetimeHours = attributeProvider.refreshTokenLifetimeHours();

        RefreshToken refreshToken = RefreshToken.newLogin(newTokenId, newSessionId, user.getId(),
                requestClientIdentifier, persistedValueProtector.protect(opaqueToken),
                lifetimeHours, currentDateTime);

        String jwtAccessToken = jwtService.generateTokenForUser(user);

        refreshTokenRepository.save(refreshToken);

        eventPublisher.publish(new LoginEvent(user.getRole(), user.getId(), newSessionId,
                user.getFullName(), user.getPhoneNumber()));

        return new LoginResult(jwtAccessToken, newTokenId + "." + rawToken, lifetimeHours);
    }
}
