package dev.hspl.hspl2shop.shop.web;

import dev.hspl.hspl2shop.shop.usage.AddressRegistrationUseCase;
import dev.hspl.hspl2shop.shop.web.request.AddressRegistrationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AddressController {
    private final AddressRegistrationUseCase registrationUseCase;

    @PostMapping("/address")
    @ResponseStatus(HttpStatus.CREATED)
    public void RegistrationUseCaseWebHandler(
            Authentication authentication,
            @RequestBody AddressRegistrationRequest payload
    ) {
        // set max address registration limitation
    }
}
