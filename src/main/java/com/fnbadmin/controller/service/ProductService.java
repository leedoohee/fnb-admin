package com.fnbadmin.controller.service;

import com.fnbadmin.controller.repository.ProductRepository;
import com.fnbadmin.controller.request.CreateProductOptionRequest;
import com.fnbadmin.controller.request.CreateProductRequest;
import com.fnbadmin.controller.request.ProductRequest;
import com.fnbadmin.controller.response.*;
import com.fnbadmin.domain.Product;
import com.fnbadmin.domain.ProductAttachFile;
import com.fnbadmin.domain.ProductOption;
import com.fnbadmin.util.ImageUtil;
import com.fnbadmin.util.Used;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional
    public boolean create(CreateProductRequest createProductRequest, List<MultipartFile> images) throws IOException {
        List<ProductAttachFile> files = new ArrayList<>();

        int productId = this.insertProduct(createProductRequest);

        if (productId <= 0) {
            return false;
        }

        createProductRequest.getOptions().forEach(po -> po.setProductId(productId));

        int optionLength = this.insertProductOptions(createProductRequest);

        if (optionLength < 0) {
            return false;
        }

        // 이미지 업로드
        for (MultipartFile image: images) {
            String path = ImageUtil.uploadImage("/Users/dooheelee/Desktop/", image.getBytes());

            files.add(ProductAttachFile.builder()
                    .createdAt(LocalDateTime.now())
                    .filePath(path)
                    .fileName(image.getOriginalFilename())
                    .productId(productId)
                    .build());
        }

        int fileSize = this.productRepository.insertAttachFile(files);

        if (fileSize < 0) {
            return false;
        }

        return true;
    }

    public PageResponse<ProductListResponse> getList(ProductRequest productRequest) {
        List<ProductListResponse> responses = new ArrayList<>();
        long totalCount         = this.productRepository.getTotalProductCount(productRequest);
        int lastPageNumber      = (int) (Math.ceil((double) totalCount / productRequest.getPageLimit()));
        List<Product> products  = this.productRepository.findProducts(productRequest);

        for (Product product : products) {
            responses.add(ProductListResponse.builder()
                            .productId(product.getProductId())
                            .name(product.getName())
                            .price(product.getPrice().intValue())
                            .status(product.getStatus())
                            .productType(product.getProductType())
                            .saleType(product.getSaleType())
                            .createdAt(String.valueOf(product.getCreatedAt()))
                            .updatedAt(String.valueOf(product.getUpdatedAt()))
                            .isUsed(product.getIsUsed().equals(Used.USED.getValue()) ? "사용" : "미사용")
                            .build());
        }

        return PageResponse.<ProductListResponse>builder()
                .last_page(lastPageNumber)
                .data(responses)
                .build();
    }

    public ProductInfoResponse getInfo(int productId) {
        Product product                                     = this.productRepository.findProduct(productId);
        List<ProductOptionResponse> productOptionResponses  = new ArrayList<>();
        List<ProductAttachFileResponse> attachFileResponses = new ArrayList<>();

        for (ProductOption productOption : product.getProductOption()) {
            productOptionResponses.add(ProductOptionResponse.builder()
                    .optionId(productOption.getOptionId())
                    .optionType(productOption.getOptionType())
                    .optionGroupId(productOption.getOptionGroupId())
                    .name(productOption.getName())
                    .price(productOption.getPrice())
                    .createdAt(String.valueOf(productOption.getCreatedAt()))
                    .updatedAt(String.valueOf(productOption.getUpdatedAt()))
                    .build());
        }

        for (ProductAttachFile attachFile : product.getProductAttachFiles()) {
            attachFileResponses.add(ProductAttachFileResponse.builder()
                            .filePath(attachFile.getFilePath())
                            .fileName(attachFile.getFileName())
                            .productId(attachFile.getProductId())
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
                .isUsed(product.getIsUsed())
                .isInfiniteQty(product.getIsInfiniteQty())
                .createdAt(String.valueOf(product.getCreatedAt()))
                .updatedAt(String.valueOf(product.getUpdatedAt()))
                .createdBy(product.getCreatedBy())
                .updatedBy(product.getUpdatedBy())
                .productOptions(productOptionResponses)
                .attachFiles(attachFileResponses)
                .build();
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
                .isUsed(createProductRequest.getIsUsed())
                .quantity(createProductRequest.getQuantity())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .createdBy("admin")
                .updatedBy("admin")
                .build();

        return this.productRepository.insertProduct(product);
    }

    private int insertProductOptions(CreateProductRequest createProductRequest) {
        List<CreateProductOptionRequest> productOptions = createProductRequest.getOptions();
        List<ProductOption> elements = new ArrayList<>();

        for (CreateProductOptionRequest createProductOptionRequest : productOptions) {
            elements.add(ProductOption.builder()
                    .productId(createProductOptionRequest.getProductId())
                    .optionType(createProductOptionRequest.getOptionType())
                    .optionGroupId(createProductOptionRequest.getOptionGroupId())
                    .name(createProductOptionRequest.getName())
                    .price(createProductOptionRequest.getPrice())
                    .isUse(1)
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .createdBy("admin")
                    .updatedBy("admin")
                    .build());
        }

        return this.productRepository.insertProductOptions(elements);
    }
}
