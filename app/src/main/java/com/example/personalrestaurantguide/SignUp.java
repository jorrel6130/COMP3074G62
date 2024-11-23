package com.example.personalrestaurantguide;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SignUp extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // Add dummy data to SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("email", "test@example.com");
        editor.putString("password", "password123");
        editor.apply();

        // Find the EditText and Button views by their IDs
        EditText emailEditText = findViewById(R.id.emailEditText);
        EditText passwordEditText = findViewById(R.id.passwordEditText);
        Button signUpButton = findViewById(R.id.signUpButton);

        // Set an OnClickListener on the sign-up button
        signUpButton.setOnClickListener(v -> {
            // Get the text from the EditText fields
            String email = emailEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString();

            // Validate fields
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(SignUp.this, "All fields are required", Toast.LENGTH_SHORT).show();
            } else {
                // Save the sign-up details using SharedPreferences
                SharedPreferences.Editor signUpEditor = sharedPreferences.edit();
                signUpEditor.putString("email", email);
                signUpEditor.putString("password", password);
                signUpEditor.apply();

                // Provide feedback
                Toast.makeText(SignUp.this, "Sign-up successful", Toast.LENGTH_SHORT).show();

                // Redirect to Login Activity
                Intent intent = new Intent(SignUp.this, Login.class);
                startActivity(intent);
                finish(); // Close the Sign-Up activity
            }
        });
    }
}
