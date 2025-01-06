package com.egroc.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;


//public class OrderItem {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @ManyToOne
//    @JoinColumn(name = "order_id")
//    private Order order;
//
//    private String productName;
//    private int quantity;
//    private double price;


@Entity
@Table(name = "order_items")
@Getter
@Setter
@NoArgsConstructor
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false) // Foreign key to the 'Order' table
    private Order order;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false) // Foreign key to the 'Product' table
    private Product product;

    private int quantity;
    private double price;

    public OrderItem(Order order, Product product, int quantity) {
        this.order = order;
        this.product = product;
        this.quantity = quantity;
        this.price = product.getPrice();
    }

//    public String getProductName() {
//        return product != null ? product.getName() : "Unknown";
//    }

//    public OrderItem(Product product, int quantity, double price) {
//        this.product = product;
//        this.quantity = quantity;
//        this.price = price;
//    }

    public double getTotalPrice() {
        return this.price * this.quantity;
    }
}
