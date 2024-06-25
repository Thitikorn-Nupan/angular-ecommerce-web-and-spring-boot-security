package com.ttknpdev.backend.controllers;

import com.ttknpdev.backend.entities.ProductCategory;
import com.ttknpdev.backend.services.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping(value = "/api/product-category")
public class ProductCategoryControl {
    private final ProductCategoryService productCategoryService;

    @Autowired
    public ProductCategoryControl(ProductCategoryService productCategoryService) {
        this.productCategoryService = productCategoryService;
    }

    @GetMapping
    public ResponseEntity<Iterable<ProductCategory>> reads() {
        return ResponseEntity
                .accepted()
                .body(this.productCategoryService.getAllProductCategories());
    }
}
