

package com.egroc.controller;
import com.egroc.model.CartItem;
import com.egroc.model.Order;
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
import java.util.ArrayList;
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
    public String addToCart(@PathVariable int id, HttpSession session) {
        Product product = productService.getProductById(id).get();
        CartItem cartItem = new CartItem(product, 1);

        // Get existing cart from session or create a new one
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        if (cart == null) {
            cart = new ArrayList<>();
        }
        cart.add(cartItem);
        session.setAttribute("cart", cart);
        return "redirect:/shop";
    }


    @GetMapping("/cart")
    public String cartGet(Model model,HttpSession session) {
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        if (cart == null) {
            cart = new ArrayList<>(); // or return empty list if null
        }
        model.addAttribute("cartCount", cart.size());
        model.addAttribute("total", cart.stream().mapToDouble(CartItem::getTotalPrice).sum());
        model.addAttribute("cart", cart);
        return "cart";
    }

    @GetMapping("/cart/removeItem/{index}")
    public String cartItemRemove(@PathVariable int index, HttpSession session) {
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        if (cart != null && index >= 0 && index < cart.size()) {
            cart.remove(index);
            session.setAttribute("cart", cart);
        }
        return "redirect:/cart";
    }


    // Update item quantity in the cart
    @PostMapping("/cart/updateQuantity/{index}")
    public String updateQuantity(@PathVariable int index, @RequestParam int quantity, HttpSession session) {
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        if (cart != null && index >= 0 && index < cart.size()) {
            CartItem cartItem = cart.get(index);

            if (quantity < 1) {
                cartItem.setQuantity(1);
            } else if (quantity > 6) {
                cartItem.setQuantity(6);
            } else {
                cartItem.setQuantity(quantity);
            }
            session.setAttribute("cart", cart);
            // Redirect to the cart page to reflect the updated cart
        }


        return "redirect:/cart";
    }

    @GetMapping("/checkout")
    public String checkout(Model model, HttpSession session) {


        Order order = new Order();
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        if (cart == null) {
            cart = new ArrayList<>(); // or return empty list if null
        }
        model.addAttribute("order", order);
        model.addAttribute("publishableKey", publishableKey);
        model.addAttribute("cart", cart);
        model.addAttribute("total", cart.stream().mapToDouble(CartItem::getTotalPrice).sum());
        model.addAttribute("cartCount", cart.size());
        return "checkout";
    }


    @PostMapping("/checkout/update")
    public String updateCheckoutCart(@RequestParam("removeIndex") int removeIndex,HttpSession session) {


        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        if (cart != null && removeIndex >= 0 && removeIndex < cart.size()) {
            cart.remove(removeIndex);
            session.setAttribute("cart", cart);
        }
        return "redirect:/checkout";
    }


    // Process checkout

    // Process checkout
    @PostMapping("/checkout/process")
    public String processCheckout(@ModelAttribute Order order,
                                  @RequestParam(required = false) String stripeToken,
                                  RedirectAttributes redirectAttributes,HttpSession session) {
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        if (cart == null) {
            cart = new ArrayList<>();
        }
        try {

            Order savedOrder;
            // Handle checkout based on payment method
            if ("COD".equalsIgnoreCase(order.getPaymentMethod())) {
                savedOrder = checkoutService.processOrder(order.getCustomerName(), order.getAddress(), "COD",cart);
                savedOrder.setOrderStatus(com.egroc.enums.OrderStatus.PENDING);
                redirectAttributes.addFlashAttribute("message", "Order placed successfully with Cash on Delivery!");
            } else if ("Stripe".equalsIgnoreCase(order.getPaymentMethod())) {

                checkoutService.processStripeCheckout(order.getCustomerName(), order.getAddress(), stripeToken, session);
                savedOrder = checkoutService.processOrder(order.getCustomerName(), order.getAddress(), "Stripe",cart);
                savedOrder.setOrderStatus(com.egroc.enums.OrderStatus.PAID);

                redirectAttributes.addFlashAttribute("message", "Payment successful! Order placed.");
            } else {
                redirectAttributes.addFlashAttribute("message", "Invalid payment method!");
                return "order-failed";
            }

            // Save the order and add the orderId to the session
            Long orderId = orderService.saveOrder(savedOrder).getId();
            session.setAttribute("orderId", orderId);
            System.out.println("Order saved with ID: " + orderId);

            // Clear the cart after successful checkout
            session.removeAttribute("cart");


            return "order-success";

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "Checkout failed: " + e.getMessage());
            e.printStackTrace(); // Log the full exception stack trace
            return "order-failed"; // Make sure we redirect on error
        }
    }

    @GetMapping("/order-success")
    public String orderSuccess(@RequestParam("orderId") Long orderId, Model model) {
        // Fetch the order details using the orderId
        Order order = orderService.getOrderById(orderId);

        // If the order is not found, set an error message
        if (order == null) {
            model.addAttribute("error", "Order not found. Please check your order ID.");
            return "order-success"; // Display the error on the same page
        }

        // Pass order details to the model
        model.addAttribute("order", order);
        model.addAttribute("total", calculateTotal(order)); // Calculate total amount

        return "order-success"; // This will map to your `order-success.html`
    }

    // Helper method to calculate the total price
    private Double calculateTotal(Order order) {
        return order.getItems().stream()
                .mapToDouble(item -> item.getQuantity() * item.getProduct().getPrice())
                .sum();
    }





    //Order success page
//    @GetMapping("/order-success")
//    public String orderSuccess(Model model, HttpSession session) {
//        Long orderId = (Long) session.getAttribute("orderId");
//        if (orderId != null) {
//            // Retrieve the order from the database using the orderId
//            Order order = orderService.getOrderById(orderId);
//
//            // Check if the order is null
//            if (order != null) {
//                // Add the order to the model
//
//                double totalAmount = order.getItems().stream()
//                        .mapToDouble(item -> item.getQuantity() * item.getProduct().getPrice())
//                        .sum();
//                //order.setTotalAmount(totalAmount);
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
//        return "order-success";
//    }


}