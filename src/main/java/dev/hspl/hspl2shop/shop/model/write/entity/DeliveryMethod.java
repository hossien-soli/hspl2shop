package dev.hspl.hspl2shop.shop.model.write.entity;

import dev.hspl.hspl2shop.shop.value.DeliveryMethodDescription;
import dev.hspl.hspl2shop.shop.value.DeliveryMethodName;
import dev.hspl.hspl2shop.shop.value.ExternalFileReference;
import lombok.Getter;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

import java.time.LocalDateTime;

// TODO: use in-memory caching for this entity records
// this entity can only be managed by OWNER
// administration representation of DeliveryMethod entity in system
// DeliveryMethod Admin Management Entity
// موقع خرید کاربر توی بیزینس لاجیک ما به یک سری از این اطلاعات نیاز نداریم برای همین اونارو جدا میکنیم توی یک انتیتی دیگه
// فعلا به قیمت های ثابت که به سفارش اضافه میشن اکتفا میکنیم اما بعدا محاسبه هزینه ارسال یا تحویل رو پیچیده تر میکنیم

@Getter
@NullMarked
public class DeliveryMethod {
    private final short id; // not auto generated!

    private DeliveryMethodName name;

    @Nullable
    private DeliveryMethodDescription description; // very short description / varchar(255)

    @Nullable
    private ExternalFileReference iconImageReference;

    private int basePrice; // toman

    // int deliveryUnitPrice; use it in the future for custom computation of price if needed

    @Nullable
    private Short discountPercent; // 0-100

    private short maxWeightPossibleKG; // (physical weight) unit:KG - example: 10KG for post

    private boolean active;

    @Nullable
    private Short sortingValue;

    private final LocalDateTime createdAt;

    @Nullable
    private LocalDateTime updatedAt;

    @Nullable
    private final Short version;

    private DeliveryMethod(
            short id, DeliveryMethodName name, @Nullable DeliveryMethodDescription description,
            @Nullable ExternalFileReference iconImageReference, int basePrice,
            @Nullable Short discountPercent, short maxWeightPossibleKG, boolean active,
            @Nullable Short sortingValue, LocalDateTime createdAt, @Nullable LocalDateTime updatedAt,
            @Nullable Short version
    ) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.iconImageReference = iconImageReference;
        this.basePrice = basePrice;
        this.discountPercent = discountPercent;
        this.maxWeightPossibleKG = maxWeightPossibleKG;
        this.active = active;
        this.sortingValue = sortingValue;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.version = version;
    }

    public static DeliveryMethod newMethod(
            short newMethodId, DeliveryMethodName name, DeliveryMethodDescription description,
            @Nullable ExternalFileReference iconImageReference, int basePrice,
            @Nullable Short discountPercent, short maxWeightPossibleKG, @Nullable Short sortingValue,
            LocalDateTime currentDateTime
    ) {
        return new DeliveryMethod(newMethodId, name, description, iconImageReference, basePrice, discountPercent,
                maxWeightPossibleKG, false, sortingValue, currentDateTime, null, null);
    }

    public static DeliveryMethod existingMethod(
            short id, DeliveryMethodName name, @Nullable DeliveryMethodDescription description,
            @Nullable ExternalFileReference iconImageReference, int basePrice,
            @Nullable Short discountPercent, short maxWeightPossibleKG, boolean active,
            @Nullable Short sortingValue, LocalDateTime createdAt, @Nullable LocalDateTime updatedAt,
            @Nullable Short version
    ) {
        return new DeliveryMethod(id, name, description, iconImageReference, basePrice, discountPercent,
                maxWeightPossibleKG, active, sortingValue, createdAt, updatedAt, version);
    }

    public void editMethod(
            DeliveryMethodName newName, @Nullable DeliveryMethodDescription newDescription,
            @Nullable ExternalFileReference newIconImageReference, int newBasePrice,
            @Nullable Short newDiscountPercent, short newMaxWeightPossibleKG,
            @Nullable Short newSortingValue, LocalDateTime currentDateTime
    ) {
        this.name = newName;
        this.description = newDescription;
        this.iconImageReference = newIconImageReference;
        this.basePrice = newBasePrice;
        this.discountPercent = newDiscountPercent;
        this.maxWeightPossibleKG = newMaxWeightPossibleKG;
        this.sortingValue = newSortingValue;
        this.updatedAt = currentDateTime;
    }

    public void markAsActive(LocalDateTime currentDateTime) {
        this.active = true;
        this.updatedAt = currentDateTime;
    }

    public void markAsInactive(LocalDateTime currentDateTime) {
        this.active = false;
        this.updatedAt = currentDateTime;
    }
}
