package com.ttknpdev.backend.repositories;


import com.ttknpdev.backend.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepo extends JpaRepository<Product, Long> {

    // use auto generate DDL sql in JPA

    List<Product> findByProductCategoryCategoryName(@Param("name") String name);

    List<Product> findByProductCategoryId(@Param("pc_id") long pc_id);

    List<Product> findByNameContaining(@Param("name") String name);
}
