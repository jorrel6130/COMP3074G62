// Kevin Bhangu
// 101418717

package com.example.personalrestaurantguide;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class RestaurantInfoActivity extends AppCompatActivity {

    private ArrayList<RestaurantModel> restaurantModels;
    RestaurantModel currentRestaurant;
    int position;
    String name;
    String address;
    String description;
    String[] tags;
    float rating;

    EditText restaurantName;
    EditText restaurantAddress;
    EditText restaurantNotes;
    EditText restaurantTags;
    RatingBar restaurantRating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_info);

        // Pull restaurant list from SharedPrefs
        restaurantModels = PrefConfig.readListFromPref(this);

        // Sets "current restaurant" to the one in the list position provided by the intent
        Intent intent = getIntent();
        position = intent.getIntExtra("position", 0);
        currentRestaurant = restaurantModels.get(position);

        name = currentRestaurant.getName();
        address = currentRestaurant.getAddress();
        description = currentRestaurant.getDescription();
        tags = currentRestaurant.getTags();
        rating = currentRestaurant.getRating();

        // Initialize UI
        restaurantName = findViewById(R.id.etRestaurantName);
        restaurantAddress = findViewById(R.id.etRestaurantAddress);
        restaurantNotes = findViewById(R.id.etRestaurantNotes);
        restaurantTags = findViewById(R.id.etRestaurantTags2);
        restaurantRating = findViewById(R.id.rbRestaurantRating);
        ImageButton backButton = findViewById(R.id.btnBack);
        ImageButton btnSave = findViewById(R.id.btnSave);
        ImageButton shareButton = findViewById(R.id.btnShare);

        // Turning tags array into a single string
        String tagString = "";
        for (String tag : tags) {
            if (tag == tags[tags.length - 1]) {
                tagString += tag;
            } else {
                tagString = tagString + tag + ", ";
            }
        }

        // Setting text fields using restaurant values
        restaurantName.setText(name);
        restaurantAddress.setText(address);
        restaurantNotes.setText(description);
        restaurantTags.setText(tagString);
        restaurantRating.setRating(rating);

        backButton.setOnClickListener(v -> finish());

        btnSave.setOnClickListener(v -> saveRestaurantDetails());

        shareButton.setOnClickListener(v -> {
            String shareContent = "Check out this restaurant: " +
                    name + "\n" +
                    "Address: " + address + "\n" +
                    "Notes: " + description + "\n" +
                    "Rating: " + rating;
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareContent);
            startActivity(Intent.createChooser(shareIntent, "Share via"));
        });
    }

    private void saveRestaurantDetails() {

        if (name.isEmpty() || address.isEmpty()) {
            Toast.makeText(this, "Name and Address cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        name = restaurantName.getText().toString();
        description = restaurantNotes.getText().toString();
        address = restaurantAddress.getText().toString();
        tags = restaurantAddress.getText().toString().trim().split("\\s*,\\s*");
        rating = restaurantRating.getRating();

        // Sets current restaurants details to what was inputted in the EditText fields
        currentRestaurant.setName(name);
        currentRestaurant.setDescription(description);
        currentRestaurant.setAddress(address);
        currentRestaurant.setRating(rating);

        // Replaces old current restaurant with modified current restaurant
        restaurantModels.set(position, currentRestaurant);

        // Writes newly modified list into SharedPrefs
        PrefConfig.writeListInPref(getApplicationContext(), restaurantModels);

        Toast.makeText(RestaurantInfoActivity.this, ("Changes to " + name + " saved!"), Toast.LENGTH_SHORT).show();
        Intent resultIntent = new Intent(RestaurantInfoActivity.this, MainActivity.class);
        startActivity(resultIntent);
        finish();
    }
}
