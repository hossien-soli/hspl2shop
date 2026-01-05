package dev.hspl.hspl2shop.shop.model.impl.jpa.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.util.List;
import java.util.Set;

@Entity(name = "Product")
@Table(name = "products")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@NullMarked
public class Product {
    @Id
    @Column(nullable = false)
    private String id; // HumanReadableId

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(name = "name")
    private String name;

    @Column(name = "desc")
    private String shortDescription;

    @Column(name = "desc_ref")
    @Nullable
    private String longDescriptionReference;

    @Column(name = "images", columnDefinition = "SHORT_STRING[]")
    @JdbcTypeCode(SqlTypes.ARRAY)
    @Nullable
    private String[] imageReferences;

    @Column(name = "discount")
    private boolean discountFlag;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    @OrderBy("id.variantIndex DESC")
    private List<ProductVariant> variants;

    @Column(name = "visible")
    private boolean visible;

    @Column(name = "sort")
    @Nullable
    private Short sortingValue;

    @Column(name = "version")
    @Version
    @Nullable
    private Short version;
}
