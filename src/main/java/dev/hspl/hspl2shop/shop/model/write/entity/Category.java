package dev.hspl.hspl2shop.shop.model.write.entity;

import dev.hspl.hspl2shop.shop.value.CategoryName;
import dev.hspl.hspl2shop.shop.value.ExternalFileReference;
import dev.hspl.hspl2shop.shop.value.HumanReadableId;
import dev.hspl.hspl2shop.shop.value.ShortDescription;
import lombok.Getter;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.time.LocalDateTime;
import java.util.Set;

// full category
// this entity can only be managed by OWNER
// administration representation of Category entity in system
// Category Admin Management Entity

@Getter
@NullMarked
public class Category {
    private final HumanReadableId id;

    private CategoryName name;

    private ShortDescription shortDescription;

    @Nullable
    private ExternalFileReference longDescriptionReference;

    @Nullable
    private Set<ExternalFileReference> imageReferences;

    @Nullable
    private Short sortingValue;

    private final LocalDateTime createdAt;

    @Nullable
    private LocalDateTime updatedAt;

    @Nullable
    private final Short version;

    private Category(
            HumanReadableId id, CategoryName name, ShortDescription shortDescription,
            @Nullable ExternalFileReference longDescriptionReference,
            @Nullable Set<ExternalFileReference> imageReferences,
            @Nullable Short sortingValue, LocalDateTime createdAt,
            @Nullable LocalDateTime updatedAt, @Nullable Short version
    ) {
        this.id = id;
        this.name = name;
        this.shortDescription = shortDescription;
        this.longDescriptionReference = longDescriptionReference;
        this.imageReferences = imageReferences;
        this.sortingValue = sortingValue;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.version = version;
    }

    public static Category newCategory(
            HumanReadableId newCategoryId, CategoryName name, ShortDescription shortDescription,
            @Nullable ExternalFileReference longDescriptionReference,
            @Nullable Set<ExternalFileReference> imageReferences,
            @Nullable Short sortingValue, LocalDateTime currentDateTime
    ) {
        return new Category(newCategoryId, name, shortDescription, longDescriptionReference, imageReferences,
                sortingValue, currentDateTime, null, null);
    }

    public static Category existingCategory(
            HumanReadableId id, CategoryName name, ShortDescription shortDescription,
            @Nullable ExternalFileReference longDescriptionReference,
            @Nullable Set<ExternalFileReference> imageReferences,
            @Nullable Short sortingValue, LocalDateTime createdAt,
            @Nullable LocalDateTime updatedAt, @Nullable Short version
    ) {
        return new Category(id, name, shortDescription, longDescriptionReference, imageReferences,
                sortingValue, createdAt, updatedAt, version);
    }

    public void editCategory(
            CategoryName newName, ShortDescription newShortDescription,
            @Nullable ExternalFileReference newLongDescriptionReference,
            @Nullable Set<ExternalFileReference> newImageReferences,
            @Nullable Short newSortingValue, LocalDateTime currentDateTime
    ) {
        this.name = newName;
        this.shortDescription = newShortDescription;
        this.longDescriptionReference = newLongDescriptionReference;
        this.imageReferences = newImageReferences;
        this.sortingValue = newSortingValue;
        this.updatedAt = currentDateTime;
    }
}
