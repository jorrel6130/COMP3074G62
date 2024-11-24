// Gadise Oli
// 101295074

package com.example.personalrestaurantguide;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class Login extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Find the EditText and Button views by their IDs
        EditText emailEditText = findViewById(R.id.emailEditText);
        EditText passwordEditText = findViewById(R.id.passwordEditText);
        Button loginButton = findViewById(R.id.loginButton);

        // Set an OnClickListener on the login button
        loginButton.setOnClickListener(v -> {
            // Get the text from the EditText fields
            String email = emailEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();

            // Retrieve the saved sign-up details from SharedPreferences
            SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
            String savedEmail = sharedPreferences.getString("email", "");
            String savedPassword = sharedPreferences.getString("password", "");

            // Check if the input matches the saved sign-up details
            if (email.equals(savedEmail) && password.equals(savedPassword)) {
                // Navigate to MainActivity if the credentials match
                Toast.makeText(Login.this, "Login successful!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Login.this, MainActivity.class);
                startActivity(intent);
                finish(); // Close the Login activity
            } else {
                // Provide feedback if the credentials do not match
                Toast.makeText(Login.this, "Invalid email or password. Please try again.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
