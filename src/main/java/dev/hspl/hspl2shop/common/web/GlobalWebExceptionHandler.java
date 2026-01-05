package dev.hspl.hspl2shop.common.web;

import dev.hspl.hspl2shop.common.exception.ApplicationException;
import dev.hspl.hspl2shop.user.exception.PhoneVerificationLimitationException;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NullMarked;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
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
        List<ObjectError> errors = exception.getAllErrors();
        String problemKey = errors.isEmpty() ? null : errors.getFirst().getDefaultMessage();
        problemKey = problemKey != null ? problemKey : "contact_support_message";

        String defaultMessage = "something went wrong!";
        String userFriendyMessage = messageSource.getMessage(
                problemKey, null,
                defaultMessage,
                Locale.of("fa", "IR")
        );

        return new ProblemMessage(problemKey, (short) 400,
                userFriendyMessage != null ? userFriendyMessage : defaultMessage, null);
    }
}
