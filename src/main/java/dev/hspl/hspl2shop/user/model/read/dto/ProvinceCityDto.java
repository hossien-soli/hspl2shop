package dev.hspl.hspl2shop.user.model.read.dto;

import org.jspecify.annotations.NullMarked;

@NullMarked
public record ProvinceCityDto(
        short cityId,
        short provinceId,
        String provinceName,
        String cityName
) {
}
