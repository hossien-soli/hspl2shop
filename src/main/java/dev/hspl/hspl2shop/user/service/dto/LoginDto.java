package dev.hspl.hspl2shop.user.service.dto;

import jakarta.validation.constraints.NotBlank;
import org.jspecify.annotations.NullMarked;

@NullMarked
public record LoginDto(
        @NotBlank(message = "login.phone_number.required")
        String phoneNumber,

        @NotBlank(message = "login.password.required")
        String password
) {
}
