package dev.hspl.hspl2shop.user.web;

import dev.hspl.hspl2shop.user.exception.TokenRefreshFailedException;
import dev.hspl.hspl2shop.user.service.dto.*;
import dev.hspl.hspl2shop.user.service.write.UserAuthenticationService;
import dev.hspl.hspl2shop.user.value.RequestClientIdentifier;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {
    private final UserAuthenticationService authService;

    @PostMapping("/auth/customer/request")
    @ResponseStatus(HttpStatus.OK)
    public VerificationRequestResult requestCustomerPhoneVerification(
            HttpServletRequest httpRequest,
            @RequestBody @Valid RequestPhoneVerificationDto payload
    ) {
        return authService.requestCustomerPhoneVerification(
                new RequestClientIdentifier(httpRequest.getRemoteAddr()),
                payload
        );
    }

    @PostMapping("/auth/customer/register")
    @ResponseStatus(HttpStatus.OK)
    public void registerNewCustomer(
            HttpServletRequest httpRequest,
            @RequestBody @Valid CustomerRegistrationDto payload
    ) {
        authService.registerNewCustomer(
                new RequestClientIdentifier(httpRequest.getRemoteAddr()),
                payload
        );
    }

    @PostMapping("/auth/customer/password-reset")
    @ResponseStatus(HttpStatus.OK)
    public void resetCustomerPassword(
            HttpServletRequest httpRequest,
            @RequestBody @Valid PasswordResetDto payload
    ) {
        authService.resetCustomerPassword(
                new RequestClientIdentifier(httpRequest.getRemoteAddr()),
                payload
        );
    }

    @PostMapping("/auth/login")
    @ResponseStatus(HttpStatus.OK)
    public TokenResult login(
            HttpServletRequest httpRequest,
            @RequestBody @Valid LoginDto payload
    ) {
        return authService.login(
                new RequestClientIdentifier(httpRequest.getRemoteAddr()),
                payload
        );
    }

    @PostMapping("/auth/token-refresh")
    @ResponseStatus(HttpStatus.OK)
    public TokenResult tokenRefresh(
            HttpServletRequest httpRequest,
            @RequestBody @Valid TokenRefreshDto payload
    ) {
        return authService.tokenRefreshAttempt(
                new RequestClientIdentifier(httpRequest.getRemoteAddr()),
                payload
        ).orElseThrow(TokenRefreshFailedException::new);
    }
}
