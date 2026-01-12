package dev.hspl.hspl2shop.common.value;

import dev.hspl.hspl2shop.common.exception.UnacceptablePaginationParamsException;
import org.jspecify.annotations.NullMarked;

@NullMarked
public record PaginationParams(
        short pageNumber,
        short countInPage
) {
    public PaginationParams {
        boolean validate = pageNumber >= 1 && countInPage >= 5 && countInPage <= 40;
        if (!validate) {
            throw new UnacceptablePaginationParamsException(pageNumber, countInPage);
        }
    }
}
