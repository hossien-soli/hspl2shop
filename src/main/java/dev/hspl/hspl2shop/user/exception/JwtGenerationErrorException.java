package dev.hspl.hspl2shop.user.exception;

import dev.hspl.hspl2shop.common.exception.ApplicationException;
import lombok.Getter;
import org.jspecify.annotations.NullMarked;

@Getter
@NullMarked
public class JwtGenerationErrorException extends ApplicationException {
    private final String actualMessage;

    public JwtGenerationErrorException(String actualMessage) {
        super("error while generating JWT token with actual message[%s]".formatted(actualMessage));
        this.actualMessage = actualMessage;
    }

    @Override
    public String problemKey() {
        return "contact_support_message";
    }

    @Override
    public short statusCode() {
        return 500;
    }
}
