package com.example.milkerfe.model;

import org.json.JSONArray;
import org.json.JSONObject;
import java.util.List;

public class Order {
    private String orderId;
    private String memberId; // Stores customerId
    private String shippingAddress;
    private List<OrderItem> orderDetails; // Changed to List<OrderItem>

    public Order(String orderId, String memberId, String shippingAddress, List<OrderItem> orderDetails) {
        this.orderId = orderId;
        this.memberId = memberId;
        this.shippingAddress = shippingAddress;
        this.orderDetails = orderDetails;
    }

    public JSONObject toJson() throws Exception {
        JSONObject json = new JSONObject();
        json.put("customerId", memberId);
        json.put("shippingAddress", shippingAddress);

        JSONArray orderDetailsArray = new JSONArray();
        for (OrderItem item : orderDetails) {
            JSONObject detail = new JSONObject();
            detail.put("productId", item.getProductId());
            detail.put("quantity", item.getQuantity());
            orderDetailsArray.put(detail);
        }
        json.put("orderDetails", orderDetailsArray);

        return json;
    }

    public String getOrderId() { return orderId; }
}