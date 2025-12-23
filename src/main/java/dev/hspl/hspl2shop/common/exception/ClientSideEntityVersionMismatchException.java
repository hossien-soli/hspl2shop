package dev.hspl.hspl2shop.common.exception;

import lombok.Getter;
import org.jspecify.annotations.NullMarked;

@Getter
@NullMarked
public class ClientSideEntityVersionMismatchException extends ApplicationException {
    private final String entityName;
    private final String entityIdAsString;

    public ClientSideEntityVersionMismatchException(String entityName, String entityIdAsString) {
        super("client-held entity doesn't match the server-side version(outdated entity)");
        this.entityName = entityName;
        this.entityIdAsString = entityIdAsString;
    }

    @Override
    public String problemKey() {
        return "client_side_entity_version_mismatch";
    }

    @Override
    public short statusCode() {
        return 412;
    }
}
