package dev.hspl.hspl2shop.common.value;

import java.util.List;

public record PaginationResult<T>(
        List<T> items,
        short countInPage,
        short currentPage,
        short lastPage,
        long totalItems
) {
}
