package com.egroc.service;

import com.egroc.enums.OrderStatus;
import com.egroc.model.Order;
import com.egroc.model.OrderItem;
import com.egroc.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public Order getOrderById(Long id) {
        Optional<Order> order = orderRepository.findById(id);
        return order.orElse(null);
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


//    public Order calculateTotalAmount(Long orderId) {
//        Order order = orderRepository.findById(orderId)
//                .orElseThrow(() -> new RuntimeException("Order not found"));
//
//        double totalAmount = 0.0;
//
//        // Calculate total for all items in the order
//        for (OrderItem item : order.getItems()) {
//            double itemTotal = item.getQuantity() * item.getProduct().getPrice();
//            totalAmount += itemTotal;
//        }
//
//        // Optionally, apply shipping or discount logic
//        double shippingFee = 50.0; // Example shipping fee
//        totalAmount += shippingFee;
//
//        // Set the total amount in the order
//        order.setTotalAmount(totalAmount);
//        return orderRepository.save(order);  // Save the updated order with totalAmount
//    }

}

