package com.example.milkerfe.model;

public class Product {
    private String productId;
    private String productName;
    private double price;
    private int quantity;
    private String image;
    private String status;
    private String description;

    public Product(String productId, String productName, double price, int quantity) {
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
    }

    public Product(String productId, String productName, double price, int quantity, String image, String status, String description) {
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
        this.image = image;
        this.status = status;
        this.description = description;
    }

    public String getProductId() { return productId; }
    public String getProductName() { return productName; }
    public double getPrice() { return price; }
    public int getQuantity() { return quantity; }
    public String getImage() { return image; }
    public String getStatus() { return status; }
    public String getDescription() { return description; }
}