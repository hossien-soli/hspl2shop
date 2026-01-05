package dev.hspl.hspl2shop.shop.model.impl.jpa.entity;

import dev.hspl.hspl2shop.shop.value.ShortDescription;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.List;

@Entity(name = "CategoryDetail")
@Table(name = "category_detail")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@NullMarked
public class CategoryDetail {
    @Id
    private String id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId
    private Category category;

    @Column(name = "desc")
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
