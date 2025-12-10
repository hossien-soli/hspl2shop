package dev.hspl.hspl2shop.shop.model.impl.jpa;

import jakarta.persistence.*;
import lombok.*;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.time.LocalDateTime;
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

    //    @ManyToMany(fetch = FetchType.LAZY)
    //    private Set<Category> categories;

    @Column(name = "name")
    private String name;

    @Column(name = "image")
    private String primaryImage; // ImageReference

    @Column(name = "desc")
    private String shortDescription;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    @OrderBy("sortingValue DESC")
    private List<ProductVariant> variants;

    @Column(name = "visible")
    private boolean visible;

    @Column(name = "sort")
    @Nullable
    private Integer sortingValue;

    @Column(name = "version")
    @Version
    @Nullable
    private Short version;
}
