package com.egroc.model;

import com.egroc.enums.OrderStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name="orders")
@Getter
@Setter

public class Order {

    @Enumerated(EnumType.STRING)
    @NotNull
    private OrderStatus orderStatus;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String customerName;
    private String address;
    private String paymentMethod;
    private double totalAmount;






    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> items;

    // Getters, Setters, Constructors

    public Order() {}

    // Custom constructor to initialize order with customer details
    public Order(String customerName, String address, String paymentMethod) {
        this.customerName = customerName;
        this.address = address;
        this.paymentMethod = paymentMethod;
        this.items = new ArrayList<>(); // Initialize the items list
    }

    // Utility method to add an OrderItem and maintain bidirectional relationship
    public void addItem(OrderItem orderItem) {
        orderItem.setOrder(this); // Set the back-reference in OrderItem
    }
    public double getTotalAmount() {
        // Calculate the total dynamically
        return items.stream()
                .mapToDouble(item -> item.getQuantity() * item.getProduct().getPrice()) // sum of item total
                .sum();
    }
}
