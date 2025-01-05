//package com.egroc.service;
//
//import com.egroc.model.Cart;
//import com.egroc.model.Order;
//import com.egroc.repository.OrderRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//@Service
//public class CheckoutService {
//
//    @Autowired
//    private OrderRepository orderRepository;
//
//    public Order placeOrder(Order order) {
//        // Save the order to the database
//        return orderRepository.save(order);
//    }
//
//    // This method calculates the total amount based on the items in the cart
//    public double calculateTotalAmount(Cart cart) {
//        double totalAmount = 0.0;
//
//        // Assuming Cart has a method to get items and each item has a price
//        List<Item> items = cart.getItems(); // Get items from the cart
//        for (Item item : items) {
//            totalAmount += item.getPrice() * item.getQuantity(); // Calculate total
//        }
//
//        return totalAmount; // Return the calculated total amount
//    }
//}