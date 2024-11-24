package com.example.personalrestaurantguide;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RatingBar;

import androidx.appcompat.app.AppCompatActivity;

public class RestaurantInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_info);

        // Initialize UI
        EditText restaurantName = findViewById(R.id.etRestaurantName);
        EditText restaurantAddress = findViewById(R.id.etRestaurantAddress);
        EditText restaurantNotes = findViewById(R.id.etRestaurantNotes);
        RatingBar restaurantRating = findViewById(R.id.rbRestaurantRating);
        ImageButton backButton = findViewById(R.id.btnBack);
        ImageButton shareButton = findViewById(R.id.btnShare);


        Intent intent = getIntent();
        restaurantName.setText(intent.getStringExtra("name"));
        restaurantAddress.setText(intent.getStringExtra("address"));
        restaurantNotes.setText(intent.getStringExtra("notes"));
        restaurantRating.setRating(intent.getFloatExtra("rating", 0));

        backButton.setOnClickListener(v -> finish());

        shareButton.setOnClickListener(v -> {
            String shareContent = "Check out this restaurant: " +
                    restaurantName.getText().toString() + "\n" +
                    "Address: " + restaurantAddress.getText().toString() + "\n" +
                    "Notes: " + restaurantNotes.getText().toString() + "\n" +
                    "Rating: " + restaurantRating.getRating();
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareContent);
            startActivity(Intent.createChooser(shareIntent, "Share via"));
        });
    }
}
