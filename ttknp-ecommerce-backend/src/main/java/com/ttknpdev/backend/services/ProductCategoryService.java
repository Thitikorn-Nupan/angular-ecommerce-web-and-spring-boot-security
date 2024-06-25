package com.ttknpdev.backend.services;

import com.ttknpdev.backend.entities.ProductCategory;
import com.ttknpdev.backend.repositories.ProductCategoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductCategoryService {

    private ProductCategoryRepo productCategoryRepo;

    @Autowired
    public ProductCategoryService(ProductCategoryRepo productCategoryRepo) {
        this.productCategoryRepo = productCategoryRepo;
    }

    public Iterable<ProductCategory> getAllProductCategories() {
        return productCategoryRepo.findAll();
    }

}
