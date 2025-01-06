package com.egroc.global;

import com.egroc.model.CartItem;
import java.util.ArrayList;
import java.util.List;

public class GlobalData {

    public static List<CartItem> cart = new ArrayList<>();  // Store CartItems in the cart

    // Method to add a product to the cart with a quantity
    public static void addToCart(CartItem cartItem) {
        // Check if the product already exists in the cart
        for (CartItem item : cart) {
            if (item.getProduct().getId() == cartItem.getProduct().getId()) {
                item.setQuantity(item.getQuantity() + cartItem.getQuantity());  // Update quantity if product is already in the cart
                return;
            }
        }
        cart.add(cartItem);  // If product is not in the cart, add a new CartItem
    }
}
