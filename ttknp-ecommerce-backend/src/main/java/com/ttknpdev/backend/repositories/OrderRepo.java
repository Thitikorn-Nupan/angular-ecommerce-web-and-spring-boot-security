package com.ttknpdev.backend.repositories;

import com.ttknpdev.backend.entities.Order;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepo extends JpaRepository<Order, Long> {
    List<Order> findByCustomerEmailOrderByDateCreatedDesc(@Param("email") String email);
}
