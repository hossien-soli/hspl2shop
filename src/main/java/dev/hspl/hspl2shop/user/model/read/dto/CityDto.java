package dev.hspl.hspl2shop.user.model.read.dto;

import org.jspecify.annotations.NullMarked;

@NullMarked
public record CityDto(
        short id,
        String name
) {
}
