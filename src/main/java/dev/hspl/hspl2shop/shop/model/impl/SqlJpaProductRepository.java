package dev.hspl.hspl2shop.shop.model.impl;

import dev.hspl.hspl2shop.common.exception.EntityVersionMismatchException;
import dev.hspl.hspl2shop.common.value.PaginationResult;
import dev.hspl.hspl2shop.shop.model.impl.jpa.entity.ProductJpaEntity;
import dev.hspl.hspl2shop.shop.model.impl.jpa.entity.VariantId;
import dev.hspl.hspl2shop.shop.model.impl.jpa.entity.VariantJpaEntity;
import dev.hspl.hspl2shop.shop.model.impl.jpa.repository.CategoryJpaRepository;
import dev.hspl.hspl2shop.shop.model.impl.jpa.repository.ProductJpaRepository;
import dev.hspl.hspl2shop.shop.model.impl.jpa.repository.VariantJpaRepository;
import dev.hspl.hspl2shop.shop.model.read.dto.ShopProductDto;
import dev.hspl.hspl2shop.shop.model.read.dto.ShopVariantDto;
import dev.hspl.hspl2shop.shop.model.read.repository.ProductQueryRepository;
import dev.hspl.hspl2shop.shop.model.write.entity.Product;
import dev.hspl.hspl2shop.shop.model.write.entity.ProductForOrder;
import dev.hspl.hspl2shop.shop.model.write.entity.Variant;
import dev.hspl.hspl2shop.shop.model.write.repository.ProductRepository;
import dev.hspl.hspl2shop.shop.value.*;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NullMarked;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
@NullMarked
public class SqlJpaProductRepository implements ProductRepository, ProductQueryRepository {
    private final ProductJpaRepository jpaRepository;
    private final VariantJpaRepository variantJpaRepository;
    private final CategoryJpaRepository categoryJpaRepository;

    @Override
    public Optional<Product> find(HumanReadableId id) {
        Optional<ProductJpaEntity> fetchResult = jpaRepository.findByIdWithVariants(id.value());
        if (fetchResult.isEmpty()) {
            return Optional.empty();
        }

        ProductJpaEntity product = fetchResult.get();

        Set<ExternalFileReference> imageReferences = product.getImageReferences() != null ?
                Arrays.stream(product.getImageReferences()).filter(Objects::nonNull)
                        .map(ExternalFileReference::new)
                        .collect(Collectors.toSet()) : null;

        Map<Short, Variant> variants = product.getVariants().stream().map(variant -> Variant.existingVariant(
                variant.getId().getVariantIndex(), new VariantName(variant.getVariantName()),
                variant.getStockItems(), variant.getPrice(), variant.getDiscountPercent(),
                variant.getWeight(), variant.isVisible(), variant.getLastOrderedAt(), variant.getVersion()
        )).collect(Collectors.toMap(Variant::getVariantIndex, variant -> variant));

        return Optional.of(Product.existingProduct(
                new HumanReadableId(product.getId()),
                new HumanReadableId(product.getCategory().getId()),
                new ProductName(product.getName()),
                new ShortDescription(product.getShortDescription()),
                product.getLongDescriptionReference() != null ?
                        new ExternalFileReference(product.getLongDescriptionReference()) : null,
                imageReferences,
                product.isDiscounted(), product.getPriceVariantIndex(), product.getPrice(), product.isVisible(),
                Collections.unmodifiableMap(variants), product.getSortingValue(), product.getCreatedAt(),
                product.getUpdatedAt(), product.getVersion()
        ));
    }

    @Override
    public void save(Product product) throws EntityVersionMismatchException {
        try {
            String[] imageReferences = product.getImageReferences() != null ?
                    product.getImageReferences().stream()
                            .map(ExternalFileReference::value).toArray(String[]::new) : null;

            ProductJpaEntity productJpaEntity = ProductJpaEntity.builder()
                    .id(product.getId().value())
                    .category(categoryJpaRepository.getReferenceById(product.getCategoryId().value()))
                    .name(product.getName().value())
                    .shortDescription(product.getShortDescription().value())
                    .longDescriptionReference(product.getLongDescriptionReference() != null ? product.getLongDescriptionReference().value() : null)
                    .imageReferences(imageReferences)
                    .discounted(product.isDiscounted())
                    .priceVariantIndex(product.getPriceVariantIndex())
                    .price(product.getPrice())
                    .visible(product.isVisible())
                    .sortingValue(product.getSortingValue())
                    .createdAt(product.getCreatedAt())
                    .updatedAt(product.getUpdatedAt())
                    .version(product.getVersion())
                    .build();

            List<VariantJpaEntity> variants = new LinkedList<>();

            product.getVariants().values().forEach(variant -> {
                VariantJpaEntity variantJpaEntity = VariantJpaEntity.builder()
                        .id(new VariantId(product.getId().value(), variant.getVariantIndex()))
                        .product(productJpaEntity)
                        .productName(product.getName().value())
                        .variantName(variant.getVariantName().value())
                        .stockItems(variant.getStockItems())
                        .price(variant.getPrice())
                        .discountPercent(variant.getDiscountPercent())
                        .weight(variant.getWeight())
                        .visible(variant.isVisible())
                        .lastOrderedAt(variant.getLastOrderedAt())
                        .version(variant.getVersion())
                        .build();

                variants.add(variantJpaEntity);

                variantJpaRepository.save(variantJpaEntity);
            });

            productJpaEntity.setVariants(variants);
            jpaRepository.save(productJpaEntity);
        } catch (OptimisticLockingFailureException exception) {
            throw new EntityVersionMismatchException(Product.class.getSimpleName(), product.getId().value());
        }
    }

    @Override
    public PaginationResult<ShopProductDto> queryAllShop(short pageNumber, short countInPage) {
        Page<ShopProductDto> fetchResult = jpaRepository.findAllDtoForShop(PageRequest.of(pageNumber, countInPage));
        return new PaginationResult<>(
                fetchResult.getContent(),
                (short) fetchResult.getNumberOfElements(),
                (short) fetchResult.getNumber(),
                (short) fetchResult.getTotalPages(),
                fetchResult.getTotalElements()
        );
    }

    @Override
    public PaginationResult<ShopProductDto> queryByCategoryShop(HumanReadableId categoryId, short pageNumber, short countInPage) {
        Page<ShopProductDto> fetchResult = jpaRepository.findAllDtoForShopByCategoryId(categoryId.value(), PageRequest.of(pageNumber, countInPage));
        return new PaginationResult<>(
                fetchResult.getContent(),
                (short) fetchResult.getNumberOfElements(),
                (short) fetchResult.getNumber(),
                (short) fetchResult.getTotalPages(),
                fetchResult.getTotalElements()
        );
    }

    @Override
    public PaginationResult<ShopProductDto> queryDiscountedShop(short pageNumber, short countInPage) {
        Page<ShopProductDto> fetchResult = jpaRepository.findAllDtoForShopByDiscountedIsTrue(PageRequest.of(pageNumber, countInPage));
        return new PaginationResult<>(
                fetchResult.getContent(),
                (short) fetchResult.getNumberOfElements(),
                (short) fetchResult.getNumber(),
                (short) fetchResult.getTotalPages(),
                fetchResult.getTotalElements()
        );
    }

    @Override
    public List<ShopProductDto> queryByIdListShop(List<HumanReadableId> idList) {
        return jpaRepository.findAllDtoForShopByIdIn(
                idList.stream().map(HumanReadableId::value).toList()
        );
    }

    @Override
    public List<ShopVariantDto> queryProductVariants(HumanReadableId productId) {
        return variantJpaRepository.findAllDtoForShopByProductId(productId.value());
    }

    @Override
    public List<ProductForOrder> findAllForOrderByIds(Set<ProductVariantId> ids) {
        List<VariantId> jpaIds = ids.stream()
                .map(id -> VariantId.builder().productId(id.productId().value())
                        .variantIndex(id.variantIndex()).build()).toList();

        return variantJpaRepository.findAllById(jpaIds).stream()
                .map(jpaEntity -> ProductForOrder.existingProduct(
                        new ProductVariantId(
                                new HumanReadableId(jpaEntity.getId().getProductId()),
                                jpaEntity.getId().getVariantIndex()
                        ),
                        new ProductName(jpaEntity.getProductName()),
                        new VariantName(jpaEntity.getVariantName()),
                        jpaEntity.getStockItems(),
                        jpaEntity.getPrice(),
                        jpaEntity.getDiscountPercent(),
                        jpaEntity.getWeight(),
                        jpaEntity.isVisible(),
                        jpaEntity.getLastOrderedAt(),
                        jpaEntity.getVersion()
                )).toList();
    }

    @Override
    public void saveAllForOrder(List<ProductForOrder> products) throws EntityVersionMismatchException {
        for (ProductForOrder product : products) {
            try {
                VariantId variantId = VariantId.builder()
                        .productId(product.getId().productId().value())
                        .variantIndex(product.getId().variantIndex())
                        .build();

                VariantJpaEntity jpaEntity = VariantJpaEntity.builder()
                        .id(variantId)
                        .product(jpaRepository.getReferenceById(product.getId().productId().value()))
                        .productName(product.getProductName().value())
                        .variantName(product.getVariantName().value())
                        .stockItems(product.getStockItems())
                        .price(product.getPrice())
                        .discountPercent(product.getDiscountPercent())
                        .weight(product.getWeight())
                        .visible(product.isVisible())
                        .lastOrderedAt(product.getLastOrderedAt())
                        .version(product.getVersion())
                        .build();

                variantJpaRepository.saveAndFlush(jpaEntity);
            } catch (OptimisticLockingFailureException exception) {
                throw new EntityVersionMismatchException(ProductForOrder.class.getSimpleName(), product.getId().toString());
            }
        }
    }
}
