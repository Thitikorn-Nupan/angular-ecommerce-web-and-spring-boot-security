package com.ttknpdev.backend.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "order_items")
@Getter
@Setter
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long oiId;
    private String imageUrl;
    private BigDecimal price;
    private Integer quantity;
    private Long productPid; // fk mapped
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "order_oid")
    private Order order; // fk
}
