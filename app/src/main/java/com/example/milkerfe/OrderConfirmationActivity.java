package com.example.milkerfe;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class OrderConfirmationActivity extends AppCompatActivity {
    private TextView tvOrderStatus, tvOrderId, tvCustomerId, tvTotal, tvShippingAddress, tvOrderStatusValue, tvOrderDetails;
    private TextView tvOrderIdLabel, tvCustomerIdLabel, tvTotalLabel, tvShippingAddressLabel, tvOrderStatusLabel, tvOrderDetailsLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_confirmation);

        // Set the title in the default action bar
        setTitle("MilkerFE");

        tvOrderStatus = findViewById(R.id.tv_order_status);
        tvOrderIdLabel = findViewById(R.id.tv_order_id_label);
        tvOrderId = findViewById(R.id.tv_order_id);
        tvCustomerIdLabel = findViewById(R.id.tv_customer_id_label);
        tvCustomerId = findViewById(R.id.tv_customer_id);
        tvTotalLabel = findViewById(R.id.tv_total_label);
        tvTotal = findViewById(R.id.tv_total);
        tvShippingAddressLabel = findViewById(R.id.tv_shipping_address_label);
        tvShippingAddress = findViewById(R.id.tv_shipping_address);
        tvOrderStatusLabel = findViewById(R.id.tv_order_status_label);
        tvOrderStatusValue = findViewById(R.id.tv_order_status_value);
        tvOrderDetailsLabel = findViewById(R.id.tv_order_details_label);
        tvOrderDetails = findViewById(R.id.tv_order_details);

        // Get data from Intent
        boolean isSuccess = getIntent().getBooleanExtra("isSuccess", false);
        String errorMessage = getIntent().getStringExtra("errorMessage");

        if (isSuccess) {
            String orderId = getIntent().getStringExtra("orderId");
            String customerId = getIntent().getStringExtra("customerId");
            double total = getIntent().getDoubleExtra("total", 0.0);
            String shippingAddress = getIntent().getStringExtra("shippingAddress");
            String orderStatus = getIntent().getStringExtra("orderStatus");
            String orderDetails = getIntent().getStringExtra("orderDetails");

            tvOrderStatus.setText("Order Created Successfully");
            tvOrderId.setText(orderId);
            tvCustomerId.setText(customerId);
            tvTotal.setText(String.format("%.2f", total));
            tvShippingAddress.setText(shippingAddress);
            tvOrderStatusValue.setText(orderStatus);
            tvOrderDetails.setText(orderDetails);
        } else {
            tvOrderStatus.setText("Order Creation Failed");
            tvOrderIdLabel.setVisibility(TextView.GONE);
            tvOrderId.setVisibility(TextView.GONE);
            tvCustomerIdLabel.setVisibility(TextView.GONE);
            tvCustomerId.setVisibility(TextView.GONE);
            tvTotalLabel.setVisibility(TextView.GONE);
            tvTotal.setVisibility(TextView.GONE);
            tvShippingAddressLabel.setVisibility(TextView.GONE);
            tvShippingAddress.setVisibility(TextView.GONE);
            tvOrderStatusLabel.setVisibility(TextView.GONE);
            tvOrderStatusValue.setVisibility(TextView.GONE);
            tvOrderDetailsLabel.setVisibility(TextView.GONE);
            tvOrderDetails.setText(errorMessage != null ? errorMessage : "Unknown error occurred");
        }

        Button btnBackToProducts = findViewById(R.id.btn_back_to_products);
        btnBackToProducts.setOnClickListener(v -> {
            Intent intent = new Intent(OrderConfirmationActivity.this, ProductListActivity.class);
            intent.putExtra("customerId", getIntent().getStringExtra("customerId"));
            startActivity(intent);
            finish();
        });
    }
}