package dev.hspl.hspl2shop.user.service.dto;

import dev.hspl.hspl2shop.user.value.PhoneVerificationPurpose;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.jspecify.annotations.NullMarked;

// RequestPhoneVerificationDto

@NullMarked
public record RequestVerificationDto(
        @NotBlank(message = "phone_verification.phone_number.required")
        String phoneNumber,

        @NotNull(message = "phone_verification.purpose.required")
        PhoneVerificationPurpose purpose
) {
}
