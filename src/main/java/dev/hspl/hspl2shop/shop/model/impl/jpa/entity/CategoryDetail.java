package dev.hspl.hspl2shop.shop.model.impl.jpa.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

@Entity(name = "CategoryDetail")
@Table(name = "category_details")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@NullMarked
public class CategoryDetail {
    @Id
    @Column(name = "id")
    private String id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId
    private CategoryJpaEntity category;

    @Column(name = "description")
    private String shortDescription;

    @Column(name = "desc_ref")
    @Nullable
    private String longDescriptionReference;

    @Column(name = "images", columnDefinition = "SHORT_STRING[]")
    @JdbcTypeCode(SqlTypes.ARRAY)
    @Nullable
    private String[] imageReferences;

    @Column(name = "sort")
    @Nullable
    private Short sortingValue;

    @Column(name = "version")
    @Version
    @Nullable
    private Short version;
}
