package dev.hspl.hspl2shop.shop.service.write;

import dev.hspl.hspl2shop.common.ApplicationUser;
import dev.hspl.hspl2shop.common.component.ApplicationUuidGenerator;
import dev.hspl.hspl2shop.common.exception.ClientSideEntityVersionMismatchException;
import dev.hspl.hspl2shop.common.exception.UserRoleAccessException;
import dev.hspl.hspl2shop.common.value.UserAction;
import dev.hspl.hspl2shop.common.value.UserRole;
import dev.hspl.hspl2shop.shop.exception.ProductNotFoundException;
import dev.hspl.hspl2shop.shop.exception.VariantNotFoundException;
import dev.hspl.hspl2shop.shop.model.write.entity.Product;
import dev.hspl.hspl2shop.shop.model.write.entity.StockChange;
import dev.hspl.hspl2shop.shop.model.write.repository.ProductRepository;
import dev.hspl.hspl2shop.shop.model.write.repository.StockChangeRepository;
import dev.hspl.hspl2shop.shop.service.dto.ProductInfoDto;
import dev.hspl.hspl2shop.shop.service.dto.StockUpdateDto;
import dev.hspl.hspl2shop.shop.service.dto.VariantInfoDto;
import dev.hspl.hspl2shop.shop.value.*;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NullMarked;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@NullMarked
public class ProductManagementAdminService {
    private final ApplicationUuidGenerator uuidGenerator;
    private final ProductRepository productRepository;
    private final StockChangeRepository stockChangeRepository;

    public void registerNewProduct(ApplicationUser user, ProductInfoDto info) {
        boolean hasAccess = user.role().equals(UserRole.OWNER);
        if (!hasAccess) {
            throw new UserRoleAccessException(UserAction.ADMIN_REGISTER_NEW_PRODUCT, user.role());
        }

        HumanReadableId productId = new HumanReadableId(info.id());

        Set<ExternalFileReference> imageReferences = info.imageReferences() != null ?
                Arrays.stream(info.imageReferences()).filter(Objects::nonNull)
                        .map(ExternalFileReference::new)
                        .collect(Collectors.toSet()) : null;

        Product newProduct = Product.newProduct(
                productId, new HumanReadableId(info.categoryId()),
                new ProductName(info.name()), new ShortDescription(info.shortDescription()),
                info.longDescriptionReference() != null ? new ExternalFileReference(info.longDescriptionReference()) : null,
                imageReferences, info.sortingValue(), LocalDateTime.now()
        );

        productRepository.save(newProduct);
    }

    public void editProduct(ApplicationUser user, ProductInfoDto info, short clientSideVersion) {
        boolean hasAccess = user.role().equals(UserRole.OWNER);
        if (!hasAccess) {
            throw new UserRoleAccessException(UserAction.ADMIN_EDIT_PRODUCT, user.role());
        }

        var productId = new HumanReadableId(info.id());

        var newCategoryId = new HumanReadableId(info.categoryId());

        var newProductName = new ProductName(info.name());

        var newShortDescription = new ShortDescription(info.shortDescription());

        var newLongDescriptionReference = info.longDescriptionReference() != null
                ? new ExternalFileReference(info.longDescriptionReference()) : null;

        Set<ExternalFileReference> newImageReferences = info.imageReferences() != null ?
                Arrays.stream(info.imageReferences()).filter(Objects::nonNull)
                        .map(ExternalFileReference::new)
                        .collect(Collectors.toSet()) : null;

        Product product = productRepository.find(productId)
                .orElseThrow(() -> new ProductNotFoundException(productId));

        if (product.getVersion() == null || product.getVersion() != clientSideVersion) {
            throw new ClientSideEntityVersionMismatchException(Product.class.getSimpleName(), productId.value());
        }

        product.editProduct(newCategoryId, newProductName, newShortDescription,
                newLongDescriptionReference, newImageReferences, info.sortingValue(), LocalDateTime.now());

        productRepository.save(product);
    }

    public void changeProductVisibility(
            ApplicationUser user, HumanReadableId productId,
            short clientSideVersion, boolean visibility
    ) {
        boolean hasAccess = user.role().equals(UserRole.OWNER);
        if (!hasAccess) {
            throw new UserRoleAccessException(UserAction.ADMIN_CHANGE_PRODUCT_VISIBILITY, user.role());
        }

        Product product = productRepository.find(productId)
                .orElseThrow(() -> new ProductNotFoundException(productId));

        if (product.getVersion() == null || product.getVersion() != clientSideVersion) {
            throw new ClientSideEntityVersionMismatchException(Product.class.getSimpleName(), productId.value());
        }

        if (visibility) {
            product.markProductAsVisible(LocalDateTime.now());
        } else {
            product.markProductAsInvisible(LocalDateTime.now());
        }

        productRepository.save(product);
    }

    public void registerVariantForProduct(
            ApplicationUser user, HumanReadableId productId,
            VariantInfoDto info, short productClientSideVersion
    ) {
        boolean hasAccess = user.role().equals(UserRole.OWNER);
        if (!hasAccess) {
            throw new UserRoleAccessException(UserAction.ADMIN_REGISTER_VARIANT_FOR_PRODUCT, user.role());
        }

        VariantName variantName = new VariantName(info.variantName());

        Product product = productRepository.find(productId)
                .orElseThrow(() -> new ProductNotFoundException(productId));

        if (product.getVersion() == null || product.getVersion() != productClientSideVersion) {
            throw new ClientSideEntityVersionMismatchException(Product.class.getSimpleName(), productId.value());
        }

        product.addNewVariant(info.variantIndex(), variantName, info.variantPrice(),
                info.discountPercent(), LocalDateTime.now());

        productRepository.save(product);
    }

    public void editVariantOfProduct(
            ApplicationUser user, HumanReadableId productId,
            VariantInfoDto info, short productClientSideVersion
    ) {
        boolean hasAccess = user.role().equals(UserRole.OWNER);
        if (!hasAccess) {
            throw new UserRoleAccessException(UserAction.ADMIN_EDIT_VARIANT_OF_PRODUCT, user.role());
        }

        VariantName variantName = new VariantName(info.variantName());

        Product product = productRepository.find(productId)
                .orElseThrow(() -> new ProductNotFoundException(productId));

        if (product.getVersion() == null || product.getVersion() != productClientSideVersion) {
            throw new ClientSideEntityVersionMismatchException(Product.class.getSimpleName(), productId.value());
        }

        product.editVariant(info.variantIndex(), variantName, info.variantPrice(),
                info.discountPercent(), LocalDateTime.now());

        productRepository.save(product);
    }

    public void deleteVariantFromProduct(
            ApplicationUser user, HumanReadableId productId,
            short variantIndex, short productClientSideVersion
    ) {
        boolean hasAccess = user.role().equals(UserRole.OWNER);
        if (!hasAccess) {
            throw new UserRoleAccessException(UserAction.ADMIN_DELETE_VARIANT_FROM_PRODUCT, user.role());
        }

        Product product = productRepository.find(productId)
                .orElseThrow(() -> new ProductNotFoundException(productId));

        if (product.getVersion() == null || product.getVersion() != productClientSideVersion) {
            throw new ClientSideEntityVersionMismatchException(Product.class.getSimpleName(), productId.value());
        }

        product.deleteVariant(variantIndex, LocalDateTime.now());

        productRepository.save(product);
    }

    public void changeProductVariantVisibility(
            ApplicationUser user, HumanReadableId productId,
            short variantIndex, short productClientSideVersion,
            boolean visibility
    ) {
        boolean hasAccess = user.role().equals(UserRole.OWNER);
        if (!hasAccess) {
            throw new UserRoleAccessException(UserAction.ADMIN_CHANGE_PRODUCT_VARIANT_VISIBILITY, user.role());
        }

        Product product = productRepository.find(productId)
                .orElseThrow(() -> new ProductNotFoundException(productId));

        if (product.getVersion() == null || product.getVersion() != productClientSideVersion) {
            throw new ClientSideEntityVersionMismatchException(Product.class.getSimpleName(), productId.value());
        }

        if (visibility) {
            product.markVariantAsVisible(variantIndex, LocalDateTime.now());
        } else {
            product.markVariantAsInvisible(variantIndex, LocalDateTime.now());
        }

        productRepository.save(product);
    }

    public void updateProductPrice(
            ApplicationUser user, HumanReadableId productId,
            short priceVariantIndex, short productClientSideVersion
    ) {
        boolean hasAccess = user.role().equals(UserRole.OWNER);
        if (!hasAccess) {
            throw new UserRoleAccessException(UserAction.ADMIN_UPDATE_PRODUCT_PRICE, user.role());
        }

        Product product = productRepository.find(productId)
                .orElseThrow(() -> new ProductNotFoundException(productId));

        if (product.getVersion() == null || product.getVersion() != productClientSideVersion) {
            throw new ClientSideEntityVersionMismatchException(Product.class.getSimpleName(), productId.value());
        }

        product.updatePrice(priceVariantIndex, LocalDateTime.now());

        productRepository.save(product);
    }

    public void updateVariantStockItems(
            ApplicationUser user, HumanReadableId productId,
            short variantIndex, short productClientSideVersion,
            StockUpdateDto stockUpdate
    ) {
        boolean hasAccess = user.role().equals(UserRole.OWNER);
        if (!hasAccess) {
            throw new UserRoleAccessException(UserAction.ADMIN_UPDATE_VARIANT_STOCK, user.role());
        }

        Product product = productRepository.find(productId)
                .orElseThrow(() -> new ProductNotFoundException(productId));

        if (product.getVersion() == null || product.getVersion() != productClientSideVersion) {
            throw new ClientSideEntityVersionMismatchException(Product.class.getSimpleName(), productId.value());
        }

        if (!product.getVariants().containsKey(variantIndex)) {
            throw new VariantNotFoundException(product.getId(), variantIndex);
        }

        var currentDateTime = LocalDateTime.now();

        StockChange stockChange = StockChange.newChange(
                uuidGenerator.generateNew(), product.getId(), variantIndex, user.id(),
                stockUpdate.isIncrease(), product.getVariants().get(variantIndex).getStockItems(),
                stockUpdate.count(), stockUpdate.description(), currentDateTime
        );

        // this should be after stockChange object creation
        product.updateVariantStockItems(
                variantIndex, !stockUpdate.isIncrease(), stockUpdate.count(),
                currentDateTime
        );

        productRepository.save(product);
        stockChangeRepository.save(stockChange);
    }
}
