package dev.hspl.hspl2shop.shop.model.impl.jpa.entity;

import jakarta.persistence.*;
import lombok.*;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity(name = "StockChange")
@Table(name = "stock_changes")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@NullMarked
public class StockChangeJpaEntity {
    @Id
    @Column(name = "id")
    private UUID id;

    @Column(name = "user_id")
    @Nullable
    private UUID relatedUserId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumns({
            @JoinColumn(name = "product_id", referencedColumnName = "product_id"),
            @JoinColumn(name = "variant_index", referencedColumnName = "index")
    })
    private VariantJpaEntity variant;

    @Column(name = "increased")
    private boolean increased; // false=decreased

    @Column(name = "stock")
    private int stockItems; // variant stock items before change

    @Column(name = "count")
    private short count;

    @Column(name = "description")
    private String description; // short string

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "version")
    @Version
    @Nullable
    private Short version;
}
