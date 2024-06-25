package com.ttknpdev.backend.dto;

import com.ttknpdev.backend.entities.Address;
import com.ttknpdev.backend.entities.Customer;
import com.ttknpdev.backend.entities.Order;
import com.ttknpdev.backend.entities.OrderItem;
import lombok.Data;

import java.util.Set;

// *** this entity works for holding json from frontend
@Data
public class Purchase {

    private Customer customer; // convert from customer form
    private Address shippingAddress; // convert from shipping address form
    private Address billingAddress; // convert from billing address form
    private Order order;
    private Set<OrderItem> orderItems;

}
