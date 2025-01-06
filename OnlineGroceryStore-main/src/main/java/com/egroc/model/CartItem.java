//package com.egroc.model;
//
//import com.egroc.model.Product;
//
//public class CartItem {
//    private Product product;
//    private int quantity;
//
//    public CartItem(Product product, int quantity) {
//        this.product = product;
//        this.quantity = quantity;
//    }
//
//    public Product getProduct() {
//        return product;
//    }
//
//    public void setProduct(Product product) {
//        this.product = product;
//    }
//
//    public int getQuantity() {
//        return quantity;
//    }
//
//    public void setQuantity(int quantity) {
//        this.quantity = quantity;
//    }
//
//    public double getTotalPrice() {
//        return product.getPrice() * quantity;
//    }
//}
package com.egroc.model;

public class CartItem {

    private Product product;  // The product being added to the cart
    private int quantity;  // The quantity of the product

    // Constructor to initialize the CartItem
    public CartItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    // Getters and setters for product and quantity
    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    // Calculate the total price for this CartItem

    public double getTotalPrice() {
        return this.product != null ? this.product.getPrice() * this.quantity : 0.00;
    }

    public String getImageName() {
        return this.product.getImageName();
    }
}
