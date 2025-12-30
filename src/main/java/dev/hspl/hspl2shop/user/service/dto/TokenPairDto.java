package dev.hspl.hspl2shop.user.service.dto;

import org.jspecify.annotations.NullMarked;

@NullMarked
public record TokenPairDto(
        String accessToken,
        String refreshToken
) {
}
