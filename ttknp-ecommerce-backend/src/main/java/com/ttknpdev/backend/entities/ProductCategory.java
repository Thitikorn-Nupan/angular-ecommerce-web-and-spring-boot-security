package com.ttknpdev.backend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "product_categories")
@Data
public class ProductCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pc_id")
    private Long id;
    private String categoryName;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "productCategory")
    @JsonIgnore
    private List<Product> products;
}
