package dev.hspl.hspl2shop.shop.model.impl;

import dev.hspl.hspl2shop.common.exception.EntityVersionMismatchException;
import dev.hspl.hspl2shop.shop.model.impl.jpa.entity.DeliveryMethodDetail;
import dev.hspl.hspl2shop.shop.model.impl.jpa.entity.DeliveryMethodJpaEntity;
import dev.hspl.hspl2shop.shop.model.impl.jpa.repository.DeliveryMethodDetailJpaRepository;
import dev.hspl.hspl2shop.shop.model.impl.jpa.repository.DeliveryMethodJpaRepository;
import dev.hspl.hspl2shop.shop.model.read.dto.ShopDeliveryMethodDto;
import dev.hspl.hspl2shop.shop.model.read.repository.DeliveryMethodQueryRepository;
import dev.hspl.hspl2shop.shop.model.write.entity.DeliveryMethod;
import dev.hspl.hspl2shop.shop.model.write.entity.DeliveryMethodForOrder;
import dev.hspl.hspl2shop.shop.model.write.repository.DeliveryMethodRepository;
import dev.hspl.hspl2shop.shop.value.DeliveryMethodDescription;
import dev.hspl.hspl2shop.shop.value.DeliveryMethodName;
import dev.hspl.hspl2shop.shop.value.ExternalFileReference;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NullMarked;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
@NullMarked
public class SqlJpaDeliveryMethodRepository implements DeliveryMethodRepository, DeliveryMethodQueryRepository {
    private final DeliveryMethodJpaRepository jpaRepository;
    private final DeliveryMethodDetailJpaRepository detailJpaRepository;

    @Override
    public Optional<DeliveryMethod> find(short id) {
        return detailJpaRepository.findByIdWithDeliveryMethod(id).map(dmd -> DeliveryMethod.existingMethod(
                dmd.getId(), new DeliveryMethodName(dmd.getDeliveryMethod().getName()),
                dmd.getDescription() != null ? new DeliveryMethodDescription(dmd.getDescription()) : null,
                dmd.getIconImageReference() != null ? new ExternalFileReference(dmd.getIconImageReference()) : null,
                dmd.getDeliveryMethod().getBasePrice(), dmd.getDeliveryMethod().getDiscountPercent(),
                dmd.getDeliveryMethod().getMaxWeightPossibleKG(), dmd.getDeliveryMethod().isActive(),
                dmd.getSortingValue(), dmd.getCreatedAt(), dmd.getUpdatedAt(), dmd.getVersion()
        ));
    }

    @Override
    public void save(DeliveryMethod method) throws EntityVersionMismatchException {
        try {
            DeliveryMethodJpaEntity jpaEntity = DeliveryMethodJpaEntity.builder()
                    .id(method.getId())
                    .name(method.getName().value())
                    .basePrice(method.getBasePrice())
                    .discountPercent(method.getDiscountPercent())
                    .maxWeightPossibleKG(method.getMaxWeightPossibleKG())
                    .active(method.isActive())
                    .build();

            DeliveryMethodDetail detail = DeliveryMethodDetail.builder()
                    .id(method.getId())
                    .deliveryMethod(jpaEntity)
                    .description(method.getDescription() != null ? method.getDescription().value() : null)
                    .iconImageReference(method.getIconImageReference() != null ? method.getIconImageReference().value() : null)
                    .sortingValue(method.getSortingValue())
                    .createdAt(method.getCreatedAt())
                    .updatedAt(method.getUpdatedAt())
                    .version(method.getVersion())
                    .build();

            detailJpaRepository.save(detail);
        } catch (OptimisticLockingFailureException exception) {
            throw new EntityVersionMismatchException(DeliveryMethod.class.getSimpleName(), String.valueOf(method.getId()));
        }
    }

    @Override
    public Optional<DeliveryMethodForOrder> findForOrderReadOnly(short id) {
        return jpaRepository.findByIdReadOnly(id).map(jpaEntity -> new DeliveryMethodForOrder(
                jpaEntity.getId(), new DeliveryMethodName(jpaEntity.getName()),
                jpaEntity.getBasePrice(), jpaEntity.getDiscountPercent(),
                jpaEntity.getMaxWeightPossibleKG(), jpaEntity.isActive()
        ));
    }

    @Override
    public List<ShopDeliveryMethodDto> queryAllShop() {
        return detailJpaRepository.findAllDtoForShop();
    }
}
