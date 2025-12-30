package dev.hspl.hspl2shop.common;

public interface DomainEvent {
    String relatedEntityName();
    String relatedEntityIdAsString();
}
