package dev.hspl.hspl2shop.common.exception;

// TODO: add user to log context

public abstract class ApplicationException extends RuntimeException {
    public ApplicationException(String message) {
        super(message);
    }

    public abstract String problemKey();
    // a message key inside messages_fa_IR.properties files for the user (user-friendly message)

    public abstract short statusCode();
    // always enter related http status code for simplicity to exception handling
}
