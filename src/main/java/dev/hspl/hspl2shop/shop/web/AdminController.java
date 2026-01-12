package dev.hspl.hspl2shop.shop.web;

import dev.hspl.hspl2shop.common.value.PaginationResult;
import dev.hspl.hspl2shop.common.web.BaseController;
import dev.hspl.hspl2shop.shop.model.read.dto.CategoryDto;
import dev.hspl.hspl2shop.shop.service.dto.CategoryInfoDto;
import dev.hspl.hspl2shop.shop.service.dto.ProductInfoDto;
import dev.hspl.hspl2shop.shop.service.dto.StockUpdateDto;
import dev.hspl.hspl2shop.shop.service.dto.VariantInfoDto;
import dev.hspl.hspl2shop.shop.service.read.CategoryQueryAdminService;
import dev.hspl.hspl2shop.shop.service.write.CategoryManagementAdminService;
import dev.hspl.hspl2shop.shop.service.write.ProductManagementAdminService;
import dev.hspl.hspl2shop.shop.value.HumanReadableId;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AdminController extends BaseController {
    private final CategoryQueryAdminService categoryQueryService;
    private final CategoryManagementAdminService categoryManageService;
    private final ProductManagementAdminService productManageService;

    @GetMapping("/admin/category")
    @ResponseStatus(HttpStatus.OK)
    public List<CategoryDto> fetchAllCategories(Authentication authentication) {
        return categoryQueryService.fetchAllCategories(extractUser(authentication));
    }

    @PostMapping("/admin/category")
    @ResponseStatus(HttpStatus.CREATED)
    public void registerNewCategory(
            Authentication authentication,
            @RequestBody @Valid CategoryInfoDto payload
    ) {
        categoryManageService.registerNewCategory(extractUser(authentication), payload);
    }

    @PutMapping("/admin/category")
    @ResponseStatus(HttpStatus.OK)
    public void editCategory(
            Authentication authentication,
            @RequestBody @Valid CategoryInfoDto payload,
            @RequestHeader("X-Version") short clientSideVersion
    ) {
        categoryManageService.editCategory(extractUser(authentication), payload, clientSideVersion);
    }

    @DeleteMapping("/admin/category/{categoryId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteCategory(
            Authentication authentication,
            @PathVariable("categoryId") String categoryId,
            @RequestHeader("X-Version") short clientSideVersion
    ) {
        categoryManageService.deleteCategory(extractUser(authentication),
                new HumanReadableId(categoryId), clientSideVersion);
    }

//    @GetMapping("/admin/product")
//    @ResponseStatus(HttpStatus.OK)
//    public PaginationResult<ProductDto> fetchAllProducts(
//            Authentication authentication, ... filters
//    ) {
//
//    }

    @PostMapping("/admin/product")
    @ResponseStatus(HttpStatus.CREATED)
    public void registerNewProduct(
            Authentication authentication,
            @RequestBody @Valid ProductInfoDto payload
    ) {
        productManageService.registerNewProduct(extractUser(authentication), payload);
    }

    @PutMapping("/admin/product")
    @ResponseStatus(HttpStatus.OK)
    public void editProduct(
            Authentication authentication,
            @RequestBody @Valid ProductInfoDto payload,
            @RequestHeader("X-Version") short clientSideVersion
    ) {
        productManageService.editProduct(extractUser(authentication), payload, clientSideVersion);
    }

    @PatchMapping("/admin/product/{productId}/visible")
    @ResponseStatus(HttpStatus.OK)
    public void markProductAsVisible(
            Authentication authentication,
            @PathVariable("productId") String productId,
            @RequestHeader("X-Version") short clientSideVersion
    ) {
        productManageService.changeProductVisibility(
                extractUser(authentication), new HumanReadableId(productId),
                clientSideVersion, true
        );
    }

    @PatchMapping("/admin/product/{productId}/invisible")
    @ResponseStatus(HttpStatus.OK)
    public void markProductAsInvisible(
            Authentication authentication,
            @PathVariable("productId") String productId,
            @RequestHeader("X-Version") short clientSideVersion
    ) {
        productManageService.changeProductVisibility(
                extractUser(authentication), new HumanReadableId(productId),
                clientSideVersion, false
        );
    }

    @PostMapping("/admin/product/{productId}/variant")
    @ResponseStatus(HttpStatus.CREATED)
    public void registerVariantForProduct(
            Authentication authentication,
            @PathVariable("productId") String productId,
            @RequestBody @Valid VariantInfoDto payload,
            @RequestHeader("X-Version") short productClientSideVersion
    ) {
        productManageService.registerVariantForProduct(
                extractUser(authentication), new HumanReadableId(productId),
                payload, productClientSideVersion
        );
    }

    @PutMapping("/admin/product/{productId}/variant")
    @ResponseStatus(HttpStatus.OK)
    public void editVariantOfProduct(
            Authentication authentication,
            @PathVariable("productId") String productId,
            @RequestBody @Valid VariantInfoDto payload,
            @RequestHeader("X-Version") short productClientSideVersion
    ) {
        productManageService.editVariantOfProduct(
                extractUser(authentication), new HumanReadableId(productId),
                payload, productClientSideVersion
        );
    }

    @DeleteMapping("/admin/product/{productId}/variant/{variantIndex}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteVariantFromProduct(
            Authentication authentication,
            @PathVariable("productId") String productId,
            @PathVariable("variantIndex") short variantIndex,
            @RequestHeader("X-Version") short productClientSideVersion
    ) {
        productManageService.deleteVariantFromProduct(
                extractUser(authentication), new HumanReadableId(productId),
                variantIndex, productClientSideVersion
        );
    }

    @PatchMapping("/admin/product/{productId}/variant/{variantIndex}/visible")
    @ResponseStatus(HttpStatus.OK)
    public void markVariantOfProductAsVisible(
            Authentication authentication,
            @PathVariable("productId") String productId,
            @PathVariable("variantIndex") short variantIndex,
            @RequestHeader("X-Version") short productClientSideVersion
    ) {
        productManageService.changeProductVariantVisibility(
                extractUser(authentication), new HumanReadableId(productId),
                variantIndex, productClientSideVersion, true
        );
    }

    @PatchMapping("/admin/product/{productId}/variant/{variantIndex}/invisible")
    @ResponseStatus(HttpStatus.OK)
    public void markVariantOfProductAsInvisible(
            Authentication authentication,
            @PathVariable("productId") String productId,
            @PathVariable("variantIndex") short variantIndex,
            @RequestHeader("X-Version") short productClientSideVersion
    ) {
        productManageService.changeProductVariantVisibility(
                extractUser(authentication), new HumanReadableId(productId),
                variantIndex, productClientSideVersion, false
        );
    }

    @PatchMapping("/admin/product/{productId}/set-price/{priceVariantIndex}")
    @ResponseStatus(HttpStatus.OK)
    public void updateProductPrice(
            Authentication authentication,
            @PathVariable("productId") String productId,
            @PathVariable("priceVariantIndex") short priceVariantIndex,
            @RequestHeader("X-Version") short productClientSideVersion
    ) {
        productManageService.updateProductPrice(
                extractUser(authentication), new HumanReadableId(productId),
                priceVariantIndex, productClientSideVersion
        );
    }

    @PatchMapping("/admin/product/{productId}/variant/{variantIndex}/stock")
    @ResponseStatus(HttpStatus.OK)
    public void updateVariantStockItems(
            Authentication authentication,
            @PathVariable("productId") String productId,
            @PathVariable("variantIndex") short variantIndex,
            @RequestHeader("X-Version") short productClientSideVersion,
            @RequestBody @Valid StockUpdateDto payload
    ) {
        productManageService.updateVariantStockItems(
                extractUser(authentication), new HumanReadableId(productId),
                variantIndex, productClientSideVersion, payload
        );
    }
}
