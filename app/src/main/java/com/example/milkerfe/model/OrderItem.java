package com.example.milkerfe.model;

import java.io.Serializable;

public class OrderItem implements Serializable {
    private String productId;
    private int quantity;

    public OrderItem(String productId, int quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    public String getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }
}