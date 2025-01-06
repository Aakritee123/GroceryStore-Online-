//package com.egroc.service;//package com.egroc.service;
////
////import com.egroc.model.CartItem;
////import com.egroc.model.Product;
////import org.springframework.stereotype.Service;
////
////@Service
////public class CartService {
////    private List<CartItem> cartItems = new ArrayList<>(); // List of CartItems
////
////    public void addProductToCart(Product product, int quantity) {
////        // Check if the product is already in the cart
////        Optional<CartItem> existingItem = cartItems.stream()
////                .filter(item -> item.getProduct().getId().equals(product.getId()))
////                .findFirst();
////        if (existingItem.isPresent()) {
////            // Update the quantity if the product is already in the cart
////            existingItem.get().setQuantity(existingItem.get().getQuantity() + quantity);
////        } else {
////            // Add a new product to the cart
////            cartItems.add(new CartItem(product, quantity));
////        }
////    }
////
////    public List<CartItem> getCartItems() {
////        return cartItems;
////    }
////
////    public void updateQuantity(int productId, int newQuantity) {
////        cartItems.stream()
////                .filter(item -> item.getProduct().getId() == productId)
////                .forEach(item -> item.setQuantity(newQuantity));
////    }
////
////    public void removeProductFromCart(Long productId) {
////        cartItems.removeIf(item -> item.getProduct().getId() == productId);
////    }
////
////    public double calculateTotal() {
////        return cartItems.stream()
////                .mapToDouble(item -> item.getProduct().getPrice() * item.getQuantity())
////                .sum();
////    }
////}
//
//
//
//
//import com.egroc.model.Cart;
//import com.egroc.model.Product;
//import org.springframework.stereotype.Service;
//
//import jakarta.servlet.http.HttpSession;
//import java.util.ArrayList;
//import java.util.List;
//
//@Service
//public class CartService {
//
//    // Helper method to get the cart from the session
//    private Cart getCart(HttpSession session) {
//        Cart cart = (Cart) session.getAttribute("cart");
//        if (cart == null) {
//            cart = new Cart();
//            session.setAttribute("cart", cart);
//        }
//        return cart;
//    }
//
//    // Add product to the cart
//    public void addToCart(Product product, HttpSession session) {
//        Cart cart = getCart(session);
//        cart.addItem(product);
//    }
//
//    // Remove product from the cart
//    public void removeFromCart(int index, HttpSession session) {
//        Cart cart = getCart(session);
//        cart.removeItem(index);
//    }
//
//    // Update product quantity in the cart
//    public void updateQuantity(int index, int quantity, HttpSession session) {
//        Cart cart = getCart(session);
//        cart.updateQuantity(index, quantity);
//    }
//
//    // Calculate the total price of the cart
//    public double calculateTotal(HttpSession session) {
//        Cart cart = getCart(session);
//        return cart.calculateTotal();
//    }
//
//    // Get cart count (number of items)
//    public int getCartCount(HttpSession session) {
//        Cart cart = getCart(session);
//        return cart.getItemCount();
//    }
//
//    // Get cart items
//    public Cart getCartItems(HttpSession session) {
//        return getCart(session);
//    }
//}