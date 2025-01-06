package com.egroc.repository;

import com.egroc.enums.OrderStatus;
import com.egroc.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByCustomerName(String customerName);
    List<Order> findByOrderStatus(OrderStatus status);
}

