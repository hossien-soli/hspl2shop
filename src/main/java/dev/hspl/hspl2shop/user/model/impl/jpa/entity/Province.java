package dev.hspl.hspl2shop.user.model.impl.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Immutable;
import org.jspecify.annotations.NullMarked;

@Entity(name = "Province")
@Table(name = "provinces")
@NoArgsConstructor
@Getter
@Setter
@NullMarked
@Immutable
public class Province {
    @Id
    @Column(name = "id")
    private short id;

    @Column(name = "name")
    private String name;
}
