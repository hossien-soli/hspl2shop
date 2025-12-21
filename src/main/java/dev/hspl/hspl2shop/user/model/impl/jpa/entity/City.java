package dev.hspl.hspl2shop.user.model.impl.jpa.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Immutable;
import org.jspecify.annotations.NullMarked;

@Entity(name = "City")
@Table(name = "cities")
@NoArgsConstructor
@Getter
@Setter
@NullMarked
@Immutable
public class City {
    @Id
    @Column(name = "id")
    private short id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "province_id")
    private Province province;

    @Column(name = "name")
    private String name;
}
