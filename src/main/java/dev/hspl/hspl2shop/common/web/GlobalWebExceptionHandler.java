package dev.hspl.hspl2shop.common.web;

import dev.hspl.hspl2shop.common.exception.ApplicationException;
import dev.hspl.hspl2shop.user.exception.PhoneVerificationLimitationException;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NullMarked;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
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

        Object[] args = null;
        Map<String, Object> extraData = null;

        if (exception instanceof PhoneVerificationLimitationException richException) {
            args = new Object[]{richException.getSecondsDelayBetweenSessions() - richException.getSecondsElapsed()};
            extraData = Map.of(
                    "delayLimitBetweenSessions", richException.getSecondsDelayBetweenSessions(),
                    "secondsToNextSession", richException.getSecondsDelayBetweenSessions() - richException.getSecondsElapsed()
            );
        }

        String defaultMessage = "something went wrong!";
        String userFriendyMessage = messageSource.getMessage(
                problemKey, args,
                defaultMessage,
                Locale.of("fa", "IR")
        );

        return ResponseEntity.status(statusCode).body(
                new ProblemMessage(problemKey, statusCode,
                        userFriendyMessage != null ? userFriendyMessage : defaultMessage, extraData)
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ProblemMessage handleValidationException(MethodArgumentNotValidException exception) {
        System.out.println(exception.getAllErrors().stream().findFirst().get().getObjectName());
        return new ProblemMessage("xxx", (short) 400, "s", null);
        // TODO: complete this
    }
}
