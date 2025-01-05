//package com.egroc.controller;
//
//import com.egroc.model.Order;
//import com.egroc.service.CheckoutService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//
//import java.util.HashMap;
//import java.util.Map;
//
//@Controller
//public class CheckoutController {
//
//    @Autowired
//    private CheckoutService checkoutService;
//
//    // This method displays the checkout page
//    @GetMapping("/checkout")
//    public String checkout(Model model) {
//        double totalAmount = checkoutService.calculateTotalAmount();
//        model.addAttribute("total", totalAmount);
//        return "checkout"; // This should match your Thymeleaf template name
//    }
//
//    // This method handles the form submission from the checkout page
//    @PostMapping("/payNow")
//    public String payNow(Order order, Model model) {
//        // Set the total amount from the service
//        order.setTotalAmount(checkoutService.calculateTotalAmount());
//
//        // Place the order
//        Order savedOrder = checkoutService.placeOrder(order);
//
//        // Prepare the order confirmation details
//        String result = "Thank you for your order!";
//        Map<String, String> parameters = new HashMap<>();
//        parameters.put("First Name", order.getFirstName());
//        parameters.put("Last Name", order.getLastName());
//        parameters.put("Address Line 1", order.getAddressLine1());
//        parameters.put("Address Line 2", order.getAddressLine2());
//        parameters.put("Postcode", order.getPostcode());
//        parameters.put("City", order.getCity());
//        parameters.put("Phone", order.getPhone());
//        parameters.put("Email", order.getEmail());
//        parameters.put("Total Amount", "Rs. " + order.getTotalAmount());
//
//        // Add attributes to the model for the view
//        model.addAttribute("result", result);
//        model.addAttribute("parameters", parameters);
//
//        return "orderplaced"; // This should match your Thymeleaf template name for order confirmation
//    }
//}