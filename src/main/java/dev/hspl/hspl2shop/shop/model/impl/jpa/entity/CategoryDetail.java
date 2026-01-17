package dev.hspl.hspl2shop.shop.model.impl.jpa.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.time.LocalDateTime;

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
    private String id; // shared PRIMARY KEY with CategoryJpaEntity

    @OneToOne(fetch = FetchType.LAZY, optional = false, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    @MapsId
    @JoinColumn(name = "id")
    private CategoryJpaEntity category;

    @Column(name = "description")
    private String shortDescription;

    @Column(name = "desc_ref")
    @Nullable
    private String longDescriptionReference;

    @Column(name = "images")
    @JdbcTypeCode(SqlTypes.ARRAY)
    @Nullable
    private String[] imageReferences;

    @Column(name = "sort")
    @Nullable
    private Short sortingValue;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @Nullable
    private LocalDateTime updatedAt;

    @Column(name = "version")
    @Version
    @Nullable
    private Short version;
}
