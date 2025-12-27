package dev.hspl.hspl2shop.user.service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.jspecify.annotations.NullMarked;

import java.util.UUID;

@NullMarked
public record CustomerRegistrationDto(
        @NotNull(message = "registration.verification_session_id.required")
        UUID verificationSessionId,

        @NotBlank(message = "registration.verification_code.required")
        String verificationCode,

        @NotBlank(message = "registration.full_name.required")
        String fullName,

        @NotBlank(message = "registration.password.required")
        String password
) {
}
