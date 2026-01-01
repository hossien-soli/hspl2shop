package dev.hspl.hspl2shop.common.exception;

import lombok.Getter;
import org.jspecify.annotations.NullMarked;

@Getter
@NullMarked
public class ApplicationAttributeNotDefinedException extends ApplicationException {
    private final String attributeName;
    private final String providerImplClassName;

    public ApplicationAttributeNotDefinedException(String attributeName, String providerImplClassName) {
        super("can not find attribute[%s] by AttributeProvider impl class name[%s]".formatted(
                attributeName,
                providerImplClassName
        ));

        this.attributeName = attributeName;
        this.providerImplClassName = providerImplClassName;
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
