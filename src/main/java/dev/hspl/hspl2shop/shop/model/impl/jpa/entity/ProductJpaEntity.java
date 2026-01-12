package dev.hspl.hspl2shop.shop.model.impl.jpa.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.time.LocalDateTime;
import java.util.List;

@Entity(name = "Product")
@Table(name = "products")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@NullMarked
public class ProductJpaEntity {
    @Id
    @Column(name = "id")
    private String id; // HumanReadableId

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "category_id")
    private CategoryJpaEntity category;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String shortDescription;

    @Column(name = "desc_ref")
    @Nullable
    private String longDescriptionReference;

    @Column(name = "images")
    @JdbcTypeCode(SqlTypes.ARRAY)
    @Nullable
    private String[] imageReferences;

    @Column(name = "discounted")
    private boolean discounted;

    @Column(name = "price_index")
    @Nullable
    private Short priceVariantIndex;

    @Column(name = "price")
    @Nullable
    private Integer price;

    @Column(name = "visible")
    private boolean visible;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, orphanRemoval = true)
    //@OrderBy("id.variantIndex ASC")
    private List<VariantJpaEntity> variants;

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
