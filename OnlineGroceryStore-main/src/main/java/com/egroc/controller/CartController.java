package com.egroc.controller;

import com.egroc.global.GlobalData;
import com.egroc.model.CartItem;
import com.egroc.model.Order;
import com.egroc.model.OrderItem;
import com.egroc.model.Product;
import com.egroc.service.CheckoutService;
import com.egroc.service.ProductService;
import com.egroc.service.OrderService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.List;
import java.util.HashMap;
import java.util.stream.Collectors;

import com.stripe.Stripe;
import com.stripe.model.Charge;
import com.stripe.exception.StripeException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;


@Controller
public class CartController {
    @Value("${stripe.secret.key}")
    private String secretKey;

    @Value("${stripe.publishable.key}")
    private String publishableKey;


    String uploadDir=System.getProperty("user.dir") + "/img/productImages";

    @Autowired
    ProductService productService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private CheckoutService checkoutService;

    // Add product to cart
    @GetMapping("/addToCart/{id}")
    public String addToCart(@PathVariable int id) {
        // Get the product from the database
        Product product = productService.getProductById(id).get();

        // Create a CartItem object with quantity 1
        CartItem cartItem = new CartItem(product, 1);

        // Add the cart item to the cart
        GlobalData.cart.add(cartItem);

        return "redirect:/shop";
    }

    // Display cart with quantities
    @GetMapping("/cart")
    public String cartGet(Model model) {
        model.addAttribute("cartCount", GlobalData.cart.size());
        model.addAttribute("total", GlobalData.cart.stream().mapToDouble(CartItem::getTotalPrice).sum());
        model.addAttribute("cart", GlobalData.cart);
        return "cart";
    }

    // Remove item from cart
    @GetMapping("/cart/removeItem/{index}")
    public String cartItemRemove(@PathVariable int index) {
        GlobalData.cart.remove(index);
        return "redirect:/cart";
    }

    // Update item quantity in the cart
    // Update item quantity in the cart
    @PostMapping("/cart/updateQuantity/{index}")
    public String updateQuantity(@PathVariable int index, @RequestParam int quantity) {
        // Get the cart item by index
        CartItem cartItem = GlobalData.cart.get(index);

        // Enforce the minimum and maximum quantity
        if (quantity < 1) {
            cartItem.setQuantity(1); // Set to 1 if the quantity is less than 1
        } else if (quantity > 6) {
            cartItem.setQuantity(6); // Set to 6 if the quantity is greater than 6
        } else {
            cartItem.setQuantity(quantity); // Otherwise, update the quantity
        }

        // Redirect to the cart page to reflect the updated cart
        return "redirect:/cart";
    }





    // Update item quantity in the cart



// Checkout Page with Stripe Form
@GetMapping("/checkout")
public String checkout(Model model) {
    Order order = new Order();
    model.addAttribute("order", order);
    model.addAttribute("publishableKey", publishableKey); // Add Stripe publishable key
    model.addAttribute("cart", GlobalData.cart);
    model.addAttribute("total", GlobalData.cart.stream().mapToDouble(CartItem::getTotalPrice).sum());
    model.addAttribute("cartCount", GlobalData.cart.size());
    return "checkout";
}



    // Update Cart (Remove Item)
    @PostMapping("/checkout/update")
    public String updateCheckoutCart(@RequestParam("removeIndex") int removeIndex) {

            GlobalData.cart.remove(removeIndex);

        return "redirect:/checkout";
    }



    // Process Checkout (Payment Handling)

    // Process checkout
    @PostMapping("/checkout/process")
    public String processCheckout(@ModelAttribute Order order,
                                  @RequestParam(required = false) String stripeToken,
                                  RedirectAttributes redirectAttributes,HttpSession session) {


        // Calculate the total amount (can be from cart)
        double totalAmount = checkoutService.calculateCartTotal(GlobalData.cart);


        try {
            // Handle checkout based on payment method
            if ("COD".equalsIgnoreCase(order.getPaymentMethod())) {
                checkoutService.processCODCheckout(order.getCustomerName(), order.getAddress());
                redirectAttributes.addFlashAttribute("message", "Order placed successfully with Cash on Delivery!");
            } else if ("Stripe".equalsIgnoreCase(order.getPaymentMethod())) {
                checkoutService.processStripeCheckout(order.getCustomerName(), order.getAddress(), stripeToken);
                redirectAttributes.addFlashAttribute("message", "Payment successful! Order placed.");
            } else {
                redirectAttributes.addFlashAttribute("message", "Invalid payment method!");
                return "order-failed";
            }

            // Map cart items to order items
            List<OrderItem> orderItems = GlobalData.cart.stream()
                    .map(cartItem -> new OrderItem(order, cartItem.getProduct(), cartItem.getQuantity()))
                    .collect(Collectors.toList());
            order.setItems(orderItems);

            // Save the order and add the orderId to the session
            Long orderId = orderService.saveOrder(order).getId();
            session.setAttribute("orderId", orderId);

            // Clear the cart after successful checkout
            GlobalData.cart.clear();
            return "order-success";

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "Checkout failed: " + e.getMessage());
            return "order-failed";
        }
    }









    // Order success page
//    @GetMapping("/order-success")
//    public String orderSuccess(Model model, HttpSession session) {
//        Long orderId = (Long) session.getAttribute("orderId");
//
//
//
//
//        if (orderId != null) {
//            // Retrieve the order from the database using the orderId
//            Order order = orderService.getOrderById(orderId);
//
//            // Check if the order is null
//            if (order != null) {
//                // Add the order to the model
//
//                model.addAttribute("total", GlobalData.cart.stream().mapToDouble(CartItem::getTotalPrice).sum());
//                double totalAmount = order.getItems().stream()
//                        .mapToDouble(item -> item.getQuantity() * item.getProduct().getPrice())
//                        .sum();
//                order.setTotalAmount(totalAmount);
//
//                System.out.println("Total amount calculated: " + totalAmount);
//                model.addAttribute("total", totalAmount);
//                model.addAttribute("order", order);
//            } else {
//                model.addAttribute("error", "Order not found.");
//            }
//        } else {
//            model.addAttribute("error", "No order ID in session.");
//        }
//
//        return "order-success";  // Return the view name
//    }
//
//








}
