package dev.hspl.hspl2shop.common.exception;

import lombok.Getter;

@Getter
public class EntityVersionMismatchException extends ApplicationException {
    private final String entityName;
    private final String entityIdAsString;

    public EntityVersionMismatchException(String entityName, String entityIdAsString) {
        super("server-side entity version mismatch or optimistic concurrency control/locking error [%s][%s]".formatted(
                entityName, entityIdAsString
        ));

        this.entityName = entityName;
        this.entityIdAsString = entityIdAsString;
    }

    @Override
    public String problemKey() {
        return "require_another_try";
    }

    @Override
    public short statusCode() {
        return 409;
    }
}
