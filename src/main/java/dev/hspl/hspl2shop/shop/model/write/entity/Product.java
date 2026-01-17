package dev.hspl.hspl2shop.shop.model.write.entity;

import dev.hspl.hspl2shop.common.exception.GenericValidationException;
import dev.hspl.hspl2shop.shop.exception.*;
import dev.hspl.hspl2shop.shop.value.*;
import lombok.Getter;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

// this entity can only be managed by OWNER
// administration representation of Product entity in system
// Product Admin Management Entity
// موقع خرید کاربر توی بیزینس لاجیک ما به یک سری از این اطلاعات نیاز نداریم برای همین اونارو جدا میکنیم توی یک انتیتی دیگه

@Getter
@NullMarked
public class Product {
    private final HumanReadableId id;

    private HumanReadableId categoryId;

    private ProductName name;

    private ShortDescription shortDescription;

    @Nullable
    private ExternalFileReference longDescriptionReference;

    @Nullable
    private Set<ExternalFileReference> imageReferences;

    private boolean discounted; // don't let user set value for this handle it based on variants discounts

    @Nullable
    private Short priceVariantIndex;

    @Nullable
    private Integer price; // price of one variant / toman / fill this based on priceVariantIndex

    private boolean visible; // only when product has at least one visible variant and priceVariant is set this can be true

    private Map<Short, Variant> variants;

    @Nullable
    private Short sortingValue;

    private final LocalDateTime createdAt;

    @Nullable
    private LocalDateTime updatedAt; // updates on variants also should update this

    @Nullable
    private final Short version;

    private Product(
            HumanReadableId id, HumanReadableId categoryId, ProductName name,
            ShortDescription shortDescription, @Nullable ExternalFileReference longDescriptionReference,
            @Nullable Set<ExternalFileReference> imageReferences, boolean discounted,
            @Nullable Short priceVariantIndex, @Nullable Integer price, boolean visible,
            Map<Short, Variant> variants, @Nullable Short sortingValue, LocalDateTime createdAt,
            @Nullable LocalDateTime updatedAt, @Nullable Short version
    ) {
        this.id = id;
        this.categoryId = categoryId;
        this.name = name;
        this.shortDescription = shortDescription;
        this.longDescriptionReference = longDescriptionReference;
        this.imageReferences = imageReferences;
        this.discounted = discounted;
        this.priceVariantIndex = priceVariantIndex;
        this.price = price;
        this.visible = visible;
        this.variants = variants;
        this.sortingValue = sortingValue;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.version = version;
    }

    public static Product newProduct(
            HumanReadableId newProductId, HumanReadableId categoryId, ProductName name,
            ShortDescription shortDescription, @Nullable ExternalFileReference longDescriptionReference,
            @Nullable Set<ExternalFileReference> imageReferences,
            @Nullable Short sortingValue, LocalDateTime currentDateTime
    ) {
        return new Product(newProductId, categoryId, name, shortDescription, longDescriptionReference,
                imageReferences, false, null, null, false, Map.of(), sortingValue,
                currentDateTime, null, null);
    }

    public static Product existingProduct(
            HumanReadableId id, HumanReadableId categoryId, ProductName name,
            ShortDescription shortDescription, @Nullable ExternalFileReference longDescriptionReference,
            @Nullable Set<ExternalFileReference> imageReferences, boolean discounted,
            @Nullable Short priceVariantIndex, @Nullable Integer price, boolean visible,
            Map<Short, Variant> variants, @Nullable Short sortingValue, LocalDateTime createdAt,
            @Nullable LocalDateTime updatedAt, @Nullable Short version
    ) {
        return new Product(id, categoryId, name, shortDescription, longDescriptionReference,
                imageReferences, discounted, priceVariantIndex, price, visible, variants, sortingValue,
                createdAt, updatedAt, version);
    }

    public void editProduct(
            HumanReadableId newCategoryId, ProductName newName, ShortDescription newShortDescription,
            @Nullable ExternalFileReference newLongDescriptionReference,
            @Nullable Set<ExternalFileReference> newImageReferences,
            @Nullable Short newSortingValue, LocalDateTime currentDateTime
    ) {
        this.categoryId = newCategoryId;
        this.name = newName;
        this.shortDescription = newShortDescription;
        this.longDescriptionReference = newLongDescriptionReference;
        this.imageReferences = newImageReferences;
        this.sortingValue = newSortingValue;
        this.updatedAt = currentDateTime;
    }

    private void checkForVariantDiscount() {
        this.discounted = this.variants.values().stream()
                .anyMatch(variant -> variant.isVisible() && variant.getDiscountPercent() != null
                        && variant.getDiscountPercent() >= 1);
    }

    private void validateDiscountPercent(short discountPercent) {
        boolean validate = discountPercent >= 1 && discountPercent <= 100;
        if (!validate) {
            throw new GenericValidationException("admin.product.variant_discount_percent.unacceptable",
                    "admin is trying to set discount percent to %d".formatted(discountPercent));
        }
    }

    public void addNewVariant(
            short variantIndex, VariantName variantName, int variantPrice,
            @Nullable Short discountPercent, int weight, LocalDateTime currentDateTime
    ) {
        if (discountPercent != null) {
            validateDiscountPercent(discountPercent);
        }

        if (this.variants.containsKey(variantIndex)) {
            throw new DuplicateVariantIndexException(variantIndex);
        }

        Map<Short, Variant> newVariants = new HashMap<>(this.variants);

        newVariants.put(variantIndex, Variant.newVariant(
                variantIndex, variantName, variantPrice, discountPercent, weight
        ));

        this.variants = Collections.unmodifiableMap(newVariants);
        this.updatedAt = currentDateTime;
    }

    public void editVariant(
            short variantIndex, VariantName newVariantName,
            int newVariantPrice, @Nullable Short newDiscountPercent,
            int newWeight, LocalDateTime currentDateTime
    ) {
        if (newDiscountPercent != null) {
            validateDiscountPercent(newDiscountPercent);
        }

        if (!this.variants.containsKey(variantIndex)) {
            throw new VariantNotFoundException(this.id, variantIndex);
        }

        if (this.priceVariantIndex != null && variantIndex == this.priceVariantIndex) {
            this.price = newVariantPrice;
        }

        this.variants.get(variantIndex).editVariant(newVariantName, newVariantPrice,
                newDiscountPercent, newWeight);

        this.updatedAt = currentDateTime;

        checkForVariantDiscount();
    }

    private void checkForProductInvisibilityCondition() {
        this.visible = this.variants.values().stream()
                .anyMatch(Variant::isVisible);
    }

    private void checkForPriceVariant(short variantIndex) {
        if (this.priceVariantIndex != null && variantIndex == this.priceVariantIndex) {
            if (this.visible) {
                throw new VariantOnPriceException(this.id, variantIndex);
            } else {
                this.priceVariantIndex = null;
                this.price = null;
            }
        }
    }

    public void deleteVariant(short variantIndex, LocalDateTime currentDateTime) {
        if (!this.variants.containsKey(variantIndex)) {
            throw new VariantNotFoundException(this.id, variantIndex);
        }

        checkForPriceVariant(variantIndex);

        Map<Short, Variant> newVariants = new HashMap<>(this.variants);

        newVariants.remove(variantIndex);

        this.variants = Collections.unmodifiableMap(newVariants);
        this.updatedAt = currentDateTime;

        checkForVariantDiscount();

        if (this.visible) {
            checkForProductInvisibilityCondition();
        }
    }

    public void markVariantAsInvisible(short variantIndex, LocalDateTime currentDateTime) {
        if (!this.variants.containsKey(variantIndex)) {
            throw new VariantNotFoundException(this.id, variantIndex);
        }

        checkForPriceVariant(variantIndex);

        this.variants.get(variantIndex).markAsInvisible();
        this.updatedAt = currentDateTime;

        checkForVariantDiscount();

        if (this.visible) {
            checkForProductInvisibilityCondition();
        }
    }

    public void markVariantAsVisible(short variantIndex, LocalDateTime currentDateTime) {
        if (!this.variants.containsKey(variantIndex)) {
            throw new VariantNotFoundException(this.id, variantIndex);
        }

        this.variants.get(variantIndex).markAsVisible();
        this.updatedAt = currentDateTime;

        checkForVariantDiscount();
    }

    public void updateVariantStockItems(
            short variantIndex, boolean decrease,
            short count, LocalDateTime currentDateTime
    ) {
        if (!this.variants.containsKey(variantIndex)) {
            throw new VariantNotFoundException(this.id, variantIndex);
        }

        this.variants.get(variantIndex).updateStockItems(decrease, count);
        this.updatedAt = currentDateTime;
    }

    public void updatePrice(short priceVariantIndex, LocalDateTime currentDateTime) {
        if (!this.variants.containsKey(priceVariantIndex)) {
            throw new VariantNotFoundException(this.id, priceVariantIndex);
        }

        Variant targetVariant = this.variants.get(priceVariantIndex);

        if (!targetVariant.isVisible()) {
            throw new VariantIsNotVisibleException(this.id, priceVariantIndex);
        }

        this.priceVariantIndex = priceVariantIndex;
        this.price = targetVariant.getPrice();
        this.updatedAt = currentDateTime;
    }

    public void markProductAsVisible(LocalDateTime currentDateTime) {
        boolean hasVisibleVariant = this.variants.values().stream()
                .anyMatch(Variant::isVisible);

        boolean validate = hasVisibleVariant && price != null;

        if (!validate) {
            throw new ProductCanNotBeVisibleException(this.id);
        }

        this.visible = true;
        this.updatedAt = currentDateTime;
    }

    public void markProductAsInvisible(LocalDateTime currentDateTime) {
        this.visible = false;
        this.updatedAt = currentDateTime;

        this.priceVariantIndex = null;
        this.price = null;

        this.variants.values().forEach(Variant::markAsInvisible);

        checkForVariantDiscount();
    }
}
