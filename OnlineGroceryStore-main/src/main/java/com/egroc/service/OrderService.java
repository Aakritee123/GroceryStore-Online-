package com.egroc.service;

import com.egroc.enums.OrderStatus;
import com.egroc.model.Order;
import com.egroc.model.OrderItem;
import com.egroc.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.hibernate.Hibernate;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    public Order saveOrder(Order order) {
        return orderRepository.save(order);
    }



    // Get all orders
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }




    // Get order by ID
    public Order getOrderById(Long orderId) {
        return orderRepository.findByIdWithItems(orderId).orElse(null); // Fetch order with items
    }

    public List<Order> getOrdersByStatus(OrderStatus status) {
        return orderRepository.findByOrderStatus(status); // Assuming this method exists
    }

    // Update order status (from PENDING to SHIPPED, or other statuses)
    public void updateOrderStatus(Long id, OrderStatus status) {
        Order order = getOrderById(id); // Reuse the existing method
        order.setOrderStatus(status);   // Update status
        orderRepository.save(order);   // Save changes
    }



}

