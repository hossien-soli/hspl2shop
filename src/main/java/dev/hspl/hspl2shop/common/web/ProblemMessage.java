package dev.hspl.hspl2shop.common.web;

import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.Map;

@NullMarked
public record ProblemMessage(
        String problemKey,
        short statusCode,
        String userFriendlyMessage,
        @Nullable Map<String, Object> extraData
) {
}
