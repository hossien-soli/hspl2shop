package dev.hspl.hspl2shop.shop.model.impl.jpa;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.jspecify.annotations.NullMarked;

//@Entity(name = "Category")
@Table(name = "categories")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@NullMarked
public class Category {
    @Id
    @Column(name = "id")
    private String id; // human-readable-id

    @Column(name = "name")
    private String name;
}
