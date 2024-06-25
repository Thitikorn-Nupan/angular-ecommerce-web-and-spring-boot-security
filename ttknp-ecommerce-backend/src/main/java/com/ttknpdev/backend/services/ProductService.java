package com.ttknpdev.backend.services;

import com.ttknpdev.backend.entities.Product;
import com.ttknpdev.backend.repositories.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private ProductRepo productRepo;

    @Autowired
    public ProductService(ProductRepo productRepo) {
        this.productRepo = productRepo;
    }

    public List<Product> getAllProductsByProductCategoryName(String name) {
        return this.productRepo.findByProductCategoryCategoryName(name);
    }

    public List<Product> getAllProductsByProductCategoryId(long pc_id) {
        return this.productRepo.findByProductCategoryId(pc_id);
    }

    public List<Product> getAllProductsByName(String name) {
        return this.productRepo.findByNameContaining(name);
    }


}
