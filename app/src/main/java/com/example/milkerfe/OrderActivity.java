package com.example.milkerfe;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.milkerfe.model.Order;
import com.example.milkerfe.model.OrderItem;
import com.example.milkerfe.service.ApiService;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.List;
import java.util.UUID;

public class OrderActivity extends AppCompatActivity {
    private EditText etShippingAddress;
    private ApiService apiService;
    private String customerId;
    private List<OrderItem> orderItems; // Changed to List<OrderItem>

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        etShippingAddress = findViewById(R.id.et_shipping_address);
        apiService = new ApiService(this);
        customerId = getIntent().getStringExtra("customerId");
        orderItems = (List<OrderItem>) getIntent().getSerializableExtra("orderItems"); // Retrieve List<OrderItem>

        Button btnCreateOrder = findViewById(R.id.btn_create_order);
        btnCreateOrder.setOnClickListener(v -> {
            String shippingAddress = etShippingAddress.getText().toString().trim();

            if (shippingAddress.isEmpty()) {
                Toast.makeText(this, "Please enter shipping address", Toast.LENGTH_SHORT).show();
                return;
            }

            String orderId = UUID.randomUUID().toString();
            Order order = new Order(
                    orderId,
                    customerId,
                    shippingAddress,
                    orderItems // Use List<OrderItem>
            );

            apiService.createOrder(order, response -> {
                try {
                    // Parse the response
                    String responseOrderId = response.getString("orderId");
                    String responseCustomerId = response.getString("customerId");
                    double total = response.getDouble("total");
                    String responseShippingAddress = response.getString("shippingAddress");
                    String orderStatus = response.getString("orderStatus");

                    // Parse orderDetails
                    JSONArray orderDetailsArray = response.getJSONArray("orderDetails");
                    StringBuilder orderDetailsString = new StringBuilder();
                    for (int i = 0; i < orderDetailsArray.length(); i++) {
                        JSONObject detail = orderDetailsArray.getJSONObject(i);
                        String productId = detail.getString("productId");
                        int quantity = detail.getInt("quantity");
                        orderDetailsString.append("Product ID: ").append(productId)
                                .append(", Quantity: ").append(quantity).append("\n");
                    }

                    // Redirect to OrderConfirmationActivity with success data
                    Intent intent = new Intent(OrderActivity.this, OrderConfirmationActivity.class);
                    intent.putExtra("isSuccess", true);
                    intent.putExtra("orderId", responseOrderId);
                    intent.putExtra("customerId", responseCustomerId);
                    intent.putExtra("total", total);
                    intent.putExtra("shippingAddress", responseShippingAddress);
                    intent.putExtra("orderStatus", orderStatus);
                    intent.putExtra("orderDetails", orderDetailsString.toString());
                    startActivity(intent);
                    finish(); // Close OrderActivity
                } catch (Exception e) {
                    // Redirect to OrderConfirmationActivity with error message
                    Intent intent = new Intent(OrderActivity.this, OrderConfirmationActivity.class);
                    intent.putExtra("isSuccess", false);
                    intent.putExtra("errorMessage", "Failed to parse order response: " + e.getMessage());
                    intent.putExtra("customerId", customerId);
                    startActivity(intent);
                    finish();
                }
            }, error -> {
                // Redirect to OrderConfirmationActivity with error message
                Intent intent = new Intent(OrderActivity.this, OrderConfirmationActivity.class);
                intent.putExtra("isSuccess", false);
                intent.putExtra("errorMessage", "Order creation failed: " + error.getMessage());
                intent.putExtra("customerId", customerId);
                startActivity(intent);
                finish();
            });
        });
    }
}