package com.ttknpdev.backend.controllers;

import com.ttknpdev.backend.entities.Product;
import com.ttknpdev.backend.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping(value = "/api/product")
public class ProductControl {
    private final ProductService productService;
    @Autowired
    public ProductControl(ProductService productService) {
        this.productService = productService;
    }
    /*
    @GetMapping(value = "{name}")
    public ResponseEntity<List<Product>> readsByCategoryName(@PathVariable String name) {
        return ResponseEntity.accepted().body(this.productService.getAllProductsByProductCategoryName(name));
    }
    */

    @GetMapping(value = "/{pc_id}")
    public ResponseEntity<List<Product>> readsByCategoryId(@PathVariable long pc_id) {
        return ResponseEntity.accepted().body(this.productService.getAllProductsByProductCategoryId(pc_id));
    }

    @GetMapping(value = "/search/{name}")
    public ResponseEntity<List<Product>> readsByName(@PathVariable String name) {
        return ResponseEntity.accepted().body(this.productService.getAllProductsByName(name));
    }
}
