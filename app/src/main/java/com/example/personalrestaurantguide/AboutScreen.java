// Gadise Oli
// 101295074

package com.example.personalrestaurantguide;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;


public class AboutScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);

        // Find the buttons by their IDs
        Button loginButton = findViewById(R.id.loginButton);
        Button signUpButton = findViewById(R.id.signUpButton);

        // Set an OnClickListener on the login button
        loginButton.setOnClickListener(v -> {
            // Create an Intent to start the LoginActivity
            Intent intent = new Intent(AboutScreen.this, Login.class);
            startActivity(intent);
        });

        // Set an OnClickListener on the sign-up button
        signUpButton.setOnClickListener(v -> {
            // Create an Intent to start the SignUpActivity
            Intent intent = new Intent(AboutScreen.this, SignUp.class);
            startActivity(intent);
        });
    }
}