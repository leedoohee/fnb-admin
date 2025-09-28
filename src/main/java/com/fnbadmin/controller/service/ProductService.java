package com.fnbadmin.controller.service;

import com.fnbadmin.controller.repository.ProductRepository;
import com.fnbadmin.controller.request.CreateAdditionalOptRequest;
import com.fnbadmin.controller.request.CreateProductOptionRequest;
import com.fnbadmin.controller.request.CreateProductRequest;
import com.fnbadmin.controller.request.ProductRequest;
import com.fnbadmin.controller.response.AdditionalOptionResponse;
import com.fnbadmin.controller.response.ProductInfoResponse;
import com.fnbadmin.controller.response.ProductListResponse;
import com.fnbadmin.controller.response.ProductOptionResponse;
import com.fnbadmin.domain.AdditionalOption;
import com.fnbadmin.domain.Product;
import com.fnbadmin.domain.ProductOption;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional
    public boolean create(CreateProductRequest createProductRequest) {

        int productId = insertProduct(createProductRequest);

        if (productId <= 0) {
            return false;
        }

        createProductRequest.getProductOptions().forEach(po -> po.setProductId(productId));
        createProductRequest.getAdditionalOptions().forEach(ao -> ao.setProductId(productId));

        // 이미지 업로드
        int optionLength            = this.insertProductOptions(createProductRequest);
        int additionalOptionLength  = this.insertAdditionalOption(createProductRequest);

        if (optionLength < 0 || additionalOptionLength < 0) {
            return false;
        }

        return true;
    }

    public List<ProductListResponse> getList(ProductRequest productRequest) {
        List<ProductListResponse> responses = new ArrayList<>();

        List<Product> products = this.productRepository.findProducts(productRequest.getStartDate(),
                productRequest.getEndDate(), productRequest.getStatus(),
                productRequest.getPage(), productRequest.getPageLimit());

        for (Product product : products) {
            responses.add(ProductListResponse.builder()
                            .id(product.getId())
                            .name(product.getName())
                            .price(product.getPrice().intValue())
                            .status(product.getStatus())
                            .productType(product.getProductType())
                            .saleType(product.getSaleType())
                            .createdAt(String.valueOf(product.getCreatedAt()))
                            .updatedAt(String.valueOf(product.getUpdatedAt()))
                            .isUse(product.getIsUse() == 1 ? "사용" : "미사용")
                            .build());
        }

        return responses;
    }

    public ProductInfoResponse getInfo(int productId) {
        Product product                                             = this.productRepository.findProductById(productId);
        List<ProductOption> productOptions                          = this.productRepository.findProductOptions(productId);
        List<AdditionalOption> additionalOptions                    = this.productRepository.findAdditionalOptions(productId);
        List<ProductOptionResponse> productOptionResponses          = new ArrayList<>();
        List<AdditionalOptionResponse> additionalOptionResponses    = new ArrayList<>();

        for (ProductOption productOption : productOptions) {
            productOptionResponses.add(ProductOptionResponse.builder()
                    .id(productOption.getId())
                    .name(productOption.getName())
                    .price(productOption.getPrice())
                    .createdAt(String.valueOf(productOption.getCreatedAt()))
                    .updatedAt(String.valueOf(productOption.getUpdatedAt()))
                    .build());
        }

        for (AdditionalOption additionalOption : additionalOptions) {
            additionalOptionResponses.add(AdditionalOptionResponse.builder()
                    .id(additionalOption.getId())
                    .name(additionalOption.getName())
                    .price(additionalOption.getPrice())
                    .createdAt(additionalOption.getCreatedAt())
                    .updatedAt(additionalOption.getUpdatedAt())
                    .build());
        }

        return ProductInfoResponse.builder()
                .id(productId)
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice().intValue())
                .status(product.getStatus())
                .minQuantity(product.getMinQuantity())
                .maxQuantity(product.getMaxQuantity())
                .isInfiniteQty(product.getIsInfiniteQty())
                .productType(product.getProductType())
                .saleType(product.getSaleType())
                .isTakeOut(product.getIsTakeOut())
                .isDelivery(product.getIsDelivery())
                .isUse(product.getIsUse())
                .isInfiniteQty(product.getIsInfiniteQty())
                .createdAt(String.valueOf(product.getCreatedAt()))
                .updatedAt(String.valueOf(product.getUpdatedAt()))
                .createdBy(product.getCreatedBy())
                .updatedBy(product.getUpdatedBy())
                .productOptions(productOptionResponses)
                .additionalOptions(additionalOptionResponses)
                .build();
    }

    public List<ProductOptionResponse> getAllProductOptions() {
        List<ProductOption> productOptions = this.productRepository.findAllProductOptions();
        List<ProductOptionResponse> responses = new ArrayList<>();

        for (ProductOption productOption : productOptions) {
            responses.add(ProductOptionResponse.builder()
                    .id(productOption.getId())
                    .name(productOption.getName())
                    .price(productOption.getPrice())
                    .createdAt(String.valueOf(productOption.getCreatedAt()))
                    .updatedAt(String.valueOf(productOption.getUpdatedAt()))
                    .build());
        }

        return responses;
    }

    public List<AdditionalOptionResponse> getAllAdditionalOptions() {
        List<AdditionalOption> additionalOptions = this.productRepository.findAllAdditionalOptions();
        List<AdditionalOptionResponse> responses = new ArrayList<>();

        for (AdditionalOption additionalOption : additionalOptions) {
            responses.add(AdditionalOptionResponse.builder()
                    .id(additionalOption.getId())
                    .name(additionalOption.getName())
                    .price(additionalOption.getPrice())
                    .createdAt(additionalOption.getCreatedAt())
                    .updatedAt(additionalOption.getUpdatedAt())
                    .build());
        }

        return responses;
    }

    private int insertProduct(CreateProductRequest createProductRequest) {

        Product product = Product.builder()
                .name(createProductRequest.getName())
                .description(createProductRequest.getDescription())
                .price(BigDecimal.valueOf(createProductRequest.getPrice()))
                .status(createProductRequest.getStatus())
                .minQuantity(createProductRequest.getMinQuantity())
                .maxQuantity(createProductRequest.getMaxQuantity())
                .isInfiniteQty(createProductRequest.getIsInfiniteQty())
                .productType(createProductRequest.getProductType())
                .saleType(createProductRequest.getSaleType())
                .isTakeOut(createProductRequest.getIsTakeOut())
                .isDelivery(createProductRequest.getIsDelivery())
                .isUse(createProductRequest.getIsUse())
                .quantity(createProductRequest.getQuantity())
                .createdAt(LocalDateTime.parse(createProductRequest.getCreatedAt()))
                .updatedAt(LocalDateTime.parse(createProductRequest.getUpdatedAt()))
                .createdBy(createProductRequest.getCreatedBy())
                .updatedBy(createProductRequest.getUpdatedBy())
                .build();

        return this.productRepository.insertProduct(product);
    }

    private int insertProductOptions(CreateProductRequest createProductRequest) {
        List<CreateProductOptionRequest> productOptions = createProductRequest.getProductOptions();
        List<ProductOption> elements = new ArrayList<>();

        if (productOptions == null || productOptions.isEmpty()) {
            return 0;
        }

        for (CreateProductOptionRequest createProductOptionRequest : productOptions) {
            elements.add(ProductOption.builder()
                    .productId(createProductOptionRequest.getProductId())
                    .name(createProductOptionRequest.getName())
                    .price(createProductOptionRequest.getPrice())
                    .isUse(createProductOptionRequest.getIsUse())
                    .createdAt(LocalDateTime.parse(new Date().toString()))
                    .updatedAt(LocalDateTime.parse(new Date().toString()))
                    .createdBy("admin")
                    .updatedBy("admin")
                    .build());
        }

        return this.productRepository.insertProductOptions(elements);
    }

    private int insertAdditionalOption(CreateProductRequest createProductRequest) {
        List<CreateAdditionalOptRequest> additionalOptions = createProductRequest.getAdditionalOptions();
        List<AdditionalOption> elements = new ArrayList<>();

        if (additionalOptions == null || additionalOptions.isEmpty()) {
            return 0;
        }

        for (CreateAdditionalOptRequest createAdditionalOptRequest : additionalOptions) {
            elements.add(AdditionalOption.builder()
                    .productId(createAdditionalOptRequest.getProductId())
                    .name(createAdditionalOptRequest.getName())
                    .price(createAdditionalOptRequest.getPrice())
                    .isUse(createAdditionalOptRequest.getIsUse())
                    .createdAt(new Date().toString())
                    .updatedAt(new Date().toString())
                    .createdBy("admin")
                    .updatedBy("admin")
                    .build());
        }

        return this.productRepository.insertAdditionalOptions(elements);
    }
}
