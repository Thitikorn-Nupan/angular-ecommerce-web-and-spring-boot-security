package com.ttknpdev.backend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "customers")
@Data
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cid;
    private String firstname;
    private String lastname;
    private String email;
    @OneToMany(mappedBy = "customer",cascade = CascadeType.ALL) // CascadeType. ALL is a cascading type in Hibernate that specifies that all state transitions (create, update, delete, and refresh) should be cascaded from the parent entity to the child entities (importance)
    @JsonIgnore
    private Set<Order> orders = new HashSet<>();

    public void addOrder(Order order) {
        if (order != null) {
            orders = new HashSet<>();
        }
        orders.add(order);
        order.setCustomer(this);
    }
}
