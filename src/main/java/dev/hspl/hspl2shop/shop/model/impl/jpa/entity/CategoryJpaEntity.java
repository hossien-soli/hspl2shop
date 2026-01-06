package dev.hspl.hspl2shop.shop.model.impl.jpa.entity;

import jakarta.persistence.*;
import lombok.*;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

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

    @Column(name = "version")
    @Version
    @Nullable
    private Short version;
}
