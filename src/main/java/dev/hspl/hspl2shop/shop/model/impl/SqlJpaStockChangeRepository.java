package dev.hspl.hspl2shop.shop.model.impl;

import dev.hspl.hspl2shop.common.exception.EntityVersionMismatchException;
import dev.hspl.hspl2shop.shop.model.impl.jpa.entity.StockChangeJpaEntity;
import dev.hspl.hspl2shop.shop.model.impl.jpa.entity.VariantId;
import dev.hspl.hspl2shop.shop.model.impl.jpa.repository.StockChangeJpaRepository;
import dev.hspl.hspl2shop.shop.model.impl.jpa.repository.VariantJpaRepository;
import dev.hspl.hspl2shop.shop.model.write.entity.StockChange;
import dev.hspl.hspl2shop.shop.model.write.repository.StockChangeRepository;
import dev.hspl.hspl2shop.shop.value.HumanReadableId;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NullMarked;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
@NullMarked
public class SqlJpaStockChangeRepository implements StockChangeRepository {
    private final StockChangeJpaRepository jpaRepository;
    private final VariantJpaRepository variantJpaRepository;

    @Override
    public Optional<StockChange> find(UUID id) {
        return jpaRepository.findById(id).map(jpaEntity -> StockChange.existingChange(
                jpaEntity.getId(),
                new HumanReadableId(jpaEntity.getVariant().getId().getProductId()),
                jpaEntity.getVariant().getId().getVariantIndex(),
                jpaEntity.getRelatedUserId(),
                jpaEntity.isIncreased(),
                jpaEntity.getStockItems(),
                jpaEntity.getCount(),
                jpaEntity.getDescription(),
                jpaEntity.getCreatedAt(),
                jpaEntity.getVersion()
        ));
    }

    @Override
    public void save(StockChange change) throws EntityVersionMismatchException {
        try {
            VariantId variantId = VariantId.builder()
                    .productId(change.getProductId().value())
                    .variantIndex(change.getVariantIndex())
                    .build();

            // TODO: it may should be saveAndFlush instead of just save
            jpaRepository.save(StockChangeJpaEntity.builder()
                    .id(change.getId())
                    .relatedUserId(change.getRelatedUserId())
                    .variant(variantJpaRepository.getReferenceById(variantId))
                    .increased(change.isIncreased())
                    .stockItems(change.getStockItems())
                    .count(change.getCount())
                    .description(change.getDescription())
                    .createdAt(change.getCreatedAt())
                    .version(change.getVersion())
                    .build());
        } catch (OptimisticLockingFailureException exception) {
            throw new EntityVersionMismatchException(StockChange.class.getSimpleName(), change.getId().toString());
        }
    }
}
