package com.fnbadmin.controller;

import com.fnbadmin.controller.request.CreateProductRequest;
import com.fnbadmin.controller.request.ProductRequest;
import com.fnbadmin.controller.response.AdditionalOptionResponse;
import com.fnbadmin.controller.response.ProductInfoResponse;
import com.fnbadmin.controller.response.ProductListResponse;
import com.fnbadmin.controller.response.ProductOptionResponse;
import com.fnbadmin.controller.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

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
    public ResponseEntity<Boolean> create(CreateProductRequest createProductRequest) {
        return ResponseEntity.ok(this.productService.create(createProductRequest));
    }

    @GetMapping("/product/list")
    public ResponseEntity<List<ProductListResponse>> getProducts(ProductRequest productRequest) {
        return ResponseEntity.ok(this.productService.getList(productRequest));
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<ProductInfoResponse> getProductInfo(@PathVariable int productId) {
        return ResponseEntity.ok(this.productService.getInfo(productId));
    }

    @GetMapping("/product/option/list")
    public ResponseEntity<List<ProductOptionResponse>> getProductOptions() {
        return ResponseEntity.ok(this.productService.getAllProductOptions());
    }

    @GetMapping("/product/additional-option/list")
    public ResponseEntity<List<AdditionalOptionResponse>> getAdditionalOptions() {
        return ResponseEntity.ok(this.productService.getAllAdditionalOptions());
    }
}
