package com.ttknpdev.backend.services;

import com.ttknpdev.backend.entities.Order;
import com.ttknpdev.backend.repositories.OrderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    private OrderRepo orderRepo;
    @Autowired
    public OrderService(OrderRepo orderRepo) {
        this.orderRepo = orderRepo;
    }
    public List<Order> getOrdersByCustomerEmail(String email) {
        return this.orderRepo.findByCustomerEmailOrderByDateCreatedDesc(email);
    }
}
