package dev.hspl.hspl2shop.shop.model.impl.jpa.repository;

import dev.hspl.hspl2shop.shop.model.impl.jpa.entity.DeliveryMethodDetail;
import dev.hspl.hspl2shop.shop.model.read.dto.ShopDeliveryMethodDto;
import org.jspecify.annotations.NullMarked;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

@NullMarked
public interface DeliveryMethodDetailJpaRepository extends JpaRepository<DeliveryMethodDetail, Short> {
    @Query("SELECT dmd FROM DeliveryMethodDetail dmd JOIN FETCH dmd.deliveryMethod d WHERE dmd.id = :id")
    Optional<DeliveryMethodDetail> findByIdWithDeliveryMethod(@Param("id") short id);

    // forShop = active is true and sort by sortingValue

    @Query("""
            SELECT new dev.hspl.hspl2shop.shop.model.read.dto.ShopDeliveryMethodDto(\
            dmd.id, dm.name, dmd.description, dmd.iconImageReference, dm.basePrice, dm.discountPercent) \
            FROM DeliveryMethodDetail dmd \
            JOIN dmd.deliveryMethod dm \
            WHERE dm.active = TRUE ORDER BY dmd.sortingValue ASC""")
    List<ShopDeliveryMethodDto> findAllDtoForShop();
}
