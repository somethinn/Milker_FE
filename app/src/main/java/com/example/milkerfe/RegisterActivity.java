package com.example.milkerfe;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.milkerfe.model.User;
import com.example.milkerfe.service.ApiService;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {
    private EditText etFirstName, etLastName, etPhone, etUsername, etPassword;
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etFirstName = findViewById(R.id.et_first_name);
        etLastName = findViewById(R.id.et_last_name);
        etPhone = findViewById(R.id.et_phone);
        etUsername = findViewById(R.id.et_username);
        etPassword = findViewById(R.id.et_password);

        apiService = new ApiService(this);

        Button btnRegister = findViewById(R.id.btn_register);
        btnRegister.setOnClickListener(v -> {
            String firstName = etFirstName.getText().toString().trim();
            String lastName = etLastName.getText().toString().trim();
            String phone = etPhone.getText().toString().trim();
            String userName = etUsername.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            if (firstName.isEmpty() || lastName.isEmpty() || phone.isEmpty() || userName.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            User user = new User(
                    firstName,
                    lastName,
                    phone,
                    userName,
                    password
            );

            apiService.registerUser(user, response -> {
                Toast.makeText(this, "Registration successful", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }, error -> {
                Toast.makeText(this, "Registration failed: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            });
        });
    }
}