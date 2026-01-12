package dev.hspl.hspl2shop.common.exception;

import lombok.Getter;
import org.jspecify.annotations.NullMarked;

@Getter
@NullMarked
public class UnacceptablePaginationParamsException extends ApplicationException {
    private final short pageNumber;
    private final short countInPage;

    public UnacceptablePaginationParamsException(short pageNumber, short countInPage) {
        super("client is trying to fetch data with pageNumber[%d] and countInPage[%d]".formatted(
                pageNumber, countInPage
        ));

        this.pageNumber = pageNumber;
        this.countInPage = countInPage;
    }

    @Override
    public String problemKey() {
        return "pagination.params.unacceptable";
    }

    @Override
    public short statusCode() {
        return 400;
    }
}
