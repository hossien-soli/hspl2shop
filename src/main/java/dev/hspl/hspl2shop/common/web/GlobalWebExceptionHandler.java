package dev.hspl.hspl2shop.common.web;

import dev.hspl.hspl2shop.common.exception.ApplicationException;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NullMarked;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Locale;
import java.util.Map;

@RestControllerAdvice
@RequiredArgsConstructor
@NullMarked
public class GlobalWebExceptionHandler {
    private final MessageSource messageSource;

    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<ProblemMessage> handleApplicationExceptions(ApplicationException exception) {
        String problemKey = exception.problemKey();
        short statusCode = exception.statusCode();

        Map<String, Object> extraData = null;

        String defaultMessage = "something went wrong!";
        String userFriendyMessage = messageSource.getMessage(
                problemKey, null,
                defaultMessage,
                Locale.of("fa", "IR")
        );

        return ResponseEntity.status(statusCode).body(
                new ProblemMessage(problemKey, statusCode,
                        userFriendyMessage != null ? userFriendyMessage : defaultMessage, extraData)
        );
    }
}
