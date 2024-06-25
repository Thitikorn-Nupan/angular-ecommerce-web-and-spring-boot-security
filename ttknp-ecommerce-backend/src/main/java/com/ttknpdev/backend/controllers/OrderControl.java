package com.ttknpdev.backend.controllers;

import com.ttknpdev.backend.entities.Order;
import com.ttknpdev.backend.entities.Product;
import com.ttknpdev.backend.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping(value = "/api/order")
public class OrderControl {
    private final OrderService orderService;
    @Autowired
    public OrderControl(OrderService orderService) {
        this.orderService = orderService;
    }
    @GetMapping(value = "/search/{email}")
    public ResponseEntity<List<Order>> readsByEmail(@PathVariable String email) {
        return ResponseEntity.accepted().body(this.orderService.getOrdersByCustomerEmail(email));
    }
}
