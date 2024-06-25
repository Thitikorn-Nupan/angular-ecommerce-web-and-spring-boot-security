package com.ttknpdev.backend.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "addresses")
@Data
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long aid;
    private String province;
    private String district;
    private String street;
    private String zipcode;
    @OneToOne(cascade = CascadeType.ALL) // CascadeType. ALL is a cascading type in Hibernate that specifies that all state transitions (create, update, delete, and refresh) should be cascaded from the parent entity to the child entities (importance)
    @PrimaryKeyJoinColumn // join using primary keys by default keys have sam name
    private Order order;
}
