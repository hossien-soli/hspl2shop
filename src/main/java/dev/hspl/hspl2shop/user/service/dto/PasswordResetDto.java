package dev.hspl.hspl2shop.user.service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.jspecify.annotations.NullMarked;

import java.util.UUID;

@NullMarked
public record PasswordResetDto(
        @NotNull(message = "phone_verification.session_id.required")
        UUID verificationSessionId,

        @NotBlank(message = "phone_verification.code.required")
        String verificationCode,

        @NotBlank(message = "password_reset.new_password.required")
        String password
) {
}
