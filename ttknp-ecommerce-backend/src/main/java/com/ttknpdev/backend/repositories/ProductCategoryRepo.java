package com.ttknpdev.backend.repositories;

import com.ttknpdev.backend.entities.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductCategoryRepo extends JpaRepository<ProductCategory, Long> { }
