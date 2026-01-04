package dev.hspl.hspl2shop.user.service.dto;

import org.jspecify.annotations.NullMarked;

@NullMarked
public record TokenResult(
        String accessToken,
        String clientRefreshToken, // <refreshTokenId>.<plainOpaqueToken>
        short refreshTokenLifetimeHours
) {
}
