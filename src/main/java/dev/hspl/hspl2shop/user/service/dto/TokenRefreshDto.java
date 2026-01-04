package dev.hspl.hspl2shop.user.service.dto;

import jakarta.validation.constraints.NotBlank;
import org.jspecify.annotations.NullMarked;

@NullMarked
public record TokenRefreshDto(
        @NotBlank(message = "auth.client_refresh_token.required")
        String clientRefreshToken // <refreshTokenId>.<plainOpaqueToken>
) {
}
