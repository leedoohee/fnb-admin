package com.fnbadmin.controller;

import com.fnbadmin.controller.request.CreateProductRequest;
import com.fnbadmin.controller.request.ProductRequest;
import com.fnbadmin.controller.response.*;
import com.fnbadmin.controller.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/product")
    public String product() {
        return "product.html";
    }

    @GetMapping("/product-option")
    public String productOption() {
        return "product-option.html";
    }

    @PostMapping("/product")
    public ResponseEntity<Boolean> create(@RequestPart("productRequest") CreateProductRequest createProductRequest,
                                          @RequestPart(value = "images", required = false) List<MultipartFile> images) {
        try {
            return ResponseEntity.ok(this.productService.create(createProductRequest, images));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/product/list")
    public ResponseEntity<PageResponse<ProductListResponse>> getProducts(ProductRequest productRequest) {
        return ResponseEntity.ok(this.productService.getList(productRequest));
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<ProductInfoResponse> getProductInfo(@PathVariable int productId) {
        return ResponseEntity.ok(this.productService.getInfo(productId));
    }
}
