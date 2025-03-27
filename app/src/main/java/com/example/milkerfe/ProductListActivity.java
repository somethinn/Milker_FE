package com.example.milkerfe;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.milkerfe.adapter.ProductAdapter;
import com.example.milkerfe.model.OrderItem;
import com.example.milkerfe.model.Product;
import com.example.milkerfe.service.ApiService;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductListActivity extends AppCompatActivity {
    private ListView lvProducts;
    private ApiService apiService;
    private String customerId;
    private List<Product> productList;
    private Map<Product, Integer> selectedProducts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        // Set the title in the action bar
        setTitle("MilkerFE");

        lvProducts = findViewById(R.id.lv_products);
        apiService = new ApiService(this);
        customerId = getIntent().getStringExtra("customerId");
        selectedProducts = new HashMap<>();

        apiService.getProducts(0, products -> {
            productList = products;
            ProductAdapter adapter = new ProductAdapter(this, products);
            lvProducts.setAdapter(adapter);
        }, error -> {
            Toast.makeText(this, "Failed to load products: " + error.getMessage(), Toast.LENGTH_SHORT).show();
        });

        Button btnProceed = findViewById(R.id.btn_proceed_to_order);
        btnProceed.setOnClickListener(v -> {
            selectedProducts.clear(); // Clear previous selections
            for (int i = 0; i < lvProducts.getChildCount(); i++) {
                View itemView = lvProducts.getChildAt(i);
                EditText etOrderQuantity = itemView.findViewById(R.id.et_order_quantity);
                Product product = (Product) etOrderQuantity.getTag();
                String quantityStr = etOrderQuantity.getText().toString().trim();
                if (!quantityStr.isEmpty()) {
                    int quantity = Integer.parseInt(quantityStr);
                    if (quantity > 0 && quantity <= product.getQuantity()) {
                        selectedProducts.put(product, quantity);
                    }
                }
            }

            if (selectedProducts.isEmpty()) {
                Toast.makeText(this, "Please select at least one product", Toast.LENGTH_SHORT).show();
                return;
            }

            // Convert selectedProducts to List<OrderItem>
            List<OrderItem> orderItems = new ArrayList<>();
            for (Map.Entry<Product, Integer> entry : selectedProducts.entrySet()) {
                orderItems.add(new OrderItem(entry.getKey().getProductId(), entry.getValue()));
            }

            Intent intent = new Intent(ProductListActivity.this, OrderActivity.class);
            intent.putExtra("customerId", customerId);
            intent.putExtra("orderItems", (Serializable) orderItems);
            startActivity(intent);
        });
    }
}