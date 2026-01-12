package dev.hspl.hspl2shop.shop.model.impl.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.jspecify.annotations.NullMarked;

// don't call spring-repository save method on this entity
// use CategoryDetail and cascade types for managing this entity
// this entity is managed via the CategoryDetail JPA entity

@Entity(name = "Category")
@Table(name = "categories")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@NullMarked
public class CategoryJpaEntity {
    @Id
    @Column(name = "id")
    private String id; // human-readable-id

    @Column(name = "name")
    private String name;

//    @Column(name = "version")
//    @Version
//    @Nullable
//    private Short version;
}
