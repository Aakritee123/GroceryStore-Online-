package com.egroc.service;

import com.egroc.enums.OrderStatus;
import com.egroc.global.GlobalData;
import com.egroc.model.CartItem;
import com.egroc.model.Order;
import com.egroc.model.OrderItem;
import com.egroc.repository.OrderRepository;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CheckoutService {

    @Autowired
    private OrderRepository orderRepository;

    @Value("${stripe.secret.key}")
    private String stripeSecretKey;

    /**
     * Process checkout for Cash-on-Delivery (COD).
     */
    public void processCODCheckout(String customerName, String address) {
        // Get cart items and total amount
        List<CartItem> cartItems = getCartItems();
        double totalAmount = calculateCartTotal(cartItems); // Calculate the total amount for cart items

        // Create and save an order for COD
        Order order = createOrder(customerName, address, "COD", cartItems, totalAmount);
        order.setOrderStatus(OrderStatus.PENDING);
        orderRepository.save(order); // Save the order in the repository
    }

    /**
     * Process checkout using Stripe payment.
     */
    public void processStripeCheckout(String customerName, String address, String stripeToken) throws StripeException {
        // Get cart items and total amount
        List<CartItem> cartItems = getCartItems();
        double totalAmount = calculateCartTotal(cartItems); // Calculate the total amount for cart items

        // Perform Stripe payment
        performStripePayment(totalAmount, stripeToken);

        // Create and save an order for Stripe payment
        Order order = createOrder(customerName, address, "Stripe", cartItems, totalAmount);
        order.setOrderStatus(OrderStatus.PAID);
        orderRepository.save(order); // Save the order in the repository
    }

    /**
     * Processes the creation of an order with the given cart items and payment method.
     */
    public Order processOrder(String customerName, String address, String paymentMethod, List<CartItem> cartItems) {
        // Calculate total amount and create an Order object
        double totalAmount = calculateCartTotal(cartItems);
        return createOrder(customerName, address, paymentMethod, cartItems, totalAmount);
    }

    /**
     * Maps cart items to order items, calculates the total, and creates an Order.
     */
    private Order createOrder(String customerName, String address, String paymentMethod, List<CartItem> cartItems, double totalAmount) {
        // Initialize a new Order
        Order order = new Order();
        order.setCustomerName(customerName);
        order.setAddress(address);
        order.setPaymentMethod(paymentMethod);
        order.setTotalAmount(totalAmount); // Set the calculated total amount

        // Map cart items to order items
        List<OrderItem> orderItems = cartItems.stream()
                .map(cartItem -> convertCartItemToOrderItem(cartItem, order))
                .collect(Collectors.toList());

        // Set order items in the order
        order.setItems(orderItems);

        return order;
    }

    /**
     * Converts a CartItem into an OrderItem and establishes its relationship with the Order.
     */
    private OrderItem convertCartItemToOrderItem(CartItem cartItem, Order order) {
        OrderItem orderItem = new OrderItem();
        orderItem.setProduct(cartItem.getProduct());
        orderItem.setQuantity(cartItem.getQuantity());
        orderItem.setPrice(cartItem.getProduct().getPrice() * cartItem.getQuantity()); // Calculate item total price
        orderItem.setOrder(order); // Set the relationship with the parent Order
        return orderItem;
    }

    /**
     * Calculates the total price for all cart items.
     */
    public double calculateCartTotal(List<CartItem> cartItems) {
        return cartItems.stream()
                .mapToDouble(cartItem -> cartItem.getProduct().getPrice() * cartItem.getQuantity())
                .sum(); // Aggregate total price of all items
    }

    /**
     * Performs Stripe payment by creating a charge.
     */
    private void performStripePayment(double totalAmount, String stripeToken) throws StripeException {
        // Set the API key for Stripe
        Stripe.apiKey = stripeSecretKey;

        // Prepare Stripe charge parameters
        Map<String, Object> chargeParams = new HashMap<>();
        chargeParams.put("amount", (int) (totalAmount * 100)); // Convert total amount to cents
        chargeParams.put("currency", "usd"); // Currency for the payment
        chargeParams.put("source", stripeToken); // Stripe token from the frontend
        chargeParams.put("description", "eGrocery Purchase");

        // Create Stripe charge
        Charge.create(chargeParams);
    }

    /**
     * Retrieves the current cart items from GlobalData.
     */
    private List<CartItem> getCartItems() {
        // Returns the list of items in the global cart
        return GlobalData.cart;
    }
}