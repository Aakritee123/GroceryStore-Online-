// src/main/java/com/example/grocery/model/Cart.java
package com.egroc.model;

public class Cart {
    private int itemCount;
    private double totalAmount;

    // Getters and Setters
    public int getItemCount() {
        return itemCount;
    }

    public void setItemCount(int itemCount) {
        this.itemCount = itemCount;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }
}