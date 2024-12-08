// Kevin Bhangu
// 101418717

package com.example.personalrestaurantguide;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RestaurantCreationActivity extends AppCompatActivity {

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 100;

    private EditText etRestaurantName, etRestaurantAddress, etRestaurantNotes, etRestaurantTags;
    private RatingBar rbRestaurantRating;

    private ArrayList<RestaurantModel> restaurantModels;

    private final ActivityResultLauncher<Intent> autocompleteLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    Place place = Autocomplete.getPlaceFromIntent(result.getData());
                    etRestaurantAddress.setText(place.getAddress());
                    Toast.makeText(this, "Address selected: " + place.getAddress(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Error fetching place", Toast.LENGTH_SHORT).show();
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_creation);

        // Pull restaurant list from SharedPrefs; if none exists then a new one is made.
        restaurantModels = PrefConfig.readListFromPref(this);
        if (restaurantModels == null) {
            restaurantModels = new ArrayList<>();
        }

        // Initialize Google Places API
        Places.initialize(getApplicationContext(), "AIzaSyCR0Zds3TrAoeHuC_AV8uUt1_xCqGTgaG0");

        // Creating Places Client
        PlacesClient placesClient = Places.createClient(this);

        // Initialize UI components
        etRestaurantName = findViewById(R.id.etRestaurantName);
        etRestaurantAddress = findViewById(R.id.etRestaurantAddress);
        etRestaurantNotes = findViewById(R.id.etRestaurantNotes);
        etRestaurantTags = findViewById(R.id.etRestaurantTags);
        rbRestaurantRating = findViewById(R.id.rbRestaurantRating);
        ImageButton btnBack = findViewById(R.id.btnBack);
        ImageButton btnSave = findViewById(R.id.btnSave);

        // Set click listeners
        btnBack.setOnClickListener(v -> finish());
        btnSave.setOnClickListener(v -> saveRestaurantDetails());

        // Adds onClickListener for address field that opens the Places Autocomplete Activity
        etRestaurantAddress.setFocusable(false);
        etRestaurantAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                List<Place.Field> fieldList = Arrays.asList(Place.Field.ADDRESS,
                        Place.Field.LAT_LNG, Place.Field.NAME);
                Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY,
                        fieldList).build(RestaurantCreationActivity.this);
                startActivityForResult(intent, 100);
            }
        });
    }

    // After selecting one of the addresses from the ones listed, it is set into the address edit text field
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK) {
            Place place = Autocomplete.getPlaceFromIntent(data);
            etRestaurantAddress.setText(place.getAddress());
        }else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
            Toast.makeText(this, "Failed to retrieve address", Toast.LENGTH_SHORT).show();
        }
    }

    private void saveRestaurantDetails() {
        String name = etRestaurantName.getText().toString();
        String address = etRestaurantAddress.getText().toString();
        String notes = etRestaurantNotes.getText().toString();
        String[] tags = etRestaurantTags.getText().toString().trim().split("\\s*,\\s*");
        float rating = rbRestaurantRating.getRating();

        if (name.isEmpty() || address.isEmpty()) {
            Toast.makeText(this, "Name and Address cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        // Writes newly modified list into SharedPrefs
        RestaurantModel restaurantModel = new RestaurantModel(name, address, notes, tags, rating);
        restaurantModels.add(restaurantModel);
        PrefConfig.writeListInPref(getApplicationContext(), restaurantModels);

        Toast.makeText(RestaurantCreationActivity.this, (name + " successfully added!"), Toast.LENGTH_SHORT).show();
        Intent resultIntent = new Intent(RestaurantCreationActivity.this, MainActivity.class);
        startActivity(resultIntent);
        finish();
    }
}
