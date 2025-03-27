package com.example.milkerfe;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.milkerfe.model.User;
import com.example.milkerfe.service.ApiService;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {
    private EditText etUsername, etPassword;
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsername = findViewById(R.id.et_username);
        etPassword = findViewById(R.id.et_password);
        apiService = new ApiService(this);

        Button btnLogin = findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(v -> {
            String userName = etUsername.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            if (userName.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            // Create a temporary User object for login
            User user = new User("", "", "", userName, password);

            apiService.loginUser(user, response -> {
                try {
                    String customerId = response.getString("customerId");
                    Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, ProductListActivity.class);
                    intent.putExtra("customerId", customerId); // Pass customerId instead of userName
                    startActivity(intent);
                    finish();
                } catch (Exception e) {
                    Toast.makeText(this, "Error parsing login response: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }, error -> {
                Toast.makeText(this, "Login failed: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            });
        });

        TextView tvRegister = findViewById(R.id.tv_register);
        tvRegister.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
    }
}