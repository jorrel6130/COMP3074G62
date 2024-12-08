// Kevin Bhangu
// 101418717

package com.example.personalrestaurantguide;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RestaurantCreationActivity extends AppCompatActivity {

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 100;

    private EditText etRestaurantName, etRestaurantAddress, etRestaurantNotes, etRestaurantTags;
    private RatingBar rbRestaurantRating;
    private FusedLocationProviderClient fusedLocationClient;

    private ArrayList<RestaurantModel> restaurantModels;
    int restaurantImage = R.drawable.rest_symbol;

    private final ActivityResultLauncher<Intent> autocompleteLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    Place place = Autocomplete.getPlaceFromIntent(result.getData());
                    etRestaurantName.setText(place.getName());
                    etRestaurantAddress.setText(place.getAddress());
                    Toast.makeText(this, "Place selected: " + place.getName(), Toast.LENGTH_SHORT).show();
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

        // Initialize UI
        etRestaurantName = findViewById(R.id.etRestaurantName);
        etRestaurantAddress = findViewById(R.id.etRestaurantAddress);
        etRestaurantNotes = findViewById(R.id.etRestaurantNotes);
        etRestaurantTags = findViewById(R.id.etRestaurantTags);
        rbRestaurantRating = findViewById(R.id.rbRestaurantRating);
        ImageButton btnBack = findViewById(R.id.btnBack);
        ImageButton btnSave = findViewById(R.id.btnSave);
        Button btnUseCurrentLocation = findViewById(R.id.btnUseCurrentLocation);
        Button btnChooseImage = findViewById(R.id.btnChooseImage);
        Button btnUseGoogleImage = findViewById(R.id.btnUseGoogleImage);

        Places.initialize(getApplicationContext(), "AIzaSyAPR_ZNIlI5rt30i6vGz0uVM8lwhQM1OBo");

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        btnBack.setOnClickListener(v -> finish());
        btnSave.setOnClickListener(v -> saveRestaurantDetails());
        btnUseCurrentLocation.setOnClickListener(v -> requestLocationPermission());

        ActivityResultLauncher<Intent> imagePickerLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        Toast.makeText(this, "Image selected successfully", Toast.LENGTH_SHORT).show();
                    }
                });

        btnChooseImage.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            imagePickerLauncher.launch(intent);
        });

        btnUseGoogleImage.setOnClickListener(v -> openAutocompleteIntent());
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

    private void requestLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            fetchCurrentLocation();
        }
    }

    private void fetchCurrentLocation() {
        try {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                fusedLocationClient.getLastLocation()
                        .addOnSuccessListener(this, location -> {
                            if (location != null) {
                                String currentAddress = "Lat: " + location.getLatitude() + ", Lng: " + location.getLongitude();
                                etRestaurantAddress.setText(currentAddress);
                                Toast.makeText(this, "Current location fetched", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(this, "Unable to fetch location", Toast.LENGTH_SHORT).show();
                            }
                        });
            } else {
                Toast.makeText(this, "Location permission not granted", Toast.LENGTH_SHORT).show();
            }
        } catch (SecurityException e) {
            Toast.makeText(this, "SecurityException: Permission not granted", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    private void openAutocompleteIntent() {
        List<Place.Field> fields = Arrays.asList(Place.Field.NAME, Place.Field.ADDRESS);
        Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, fields)
                .build(this);
        autocompleteLauncher.launch(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                fetchCurrentLocation();
            } else {
                Toast.makeText(this, "Location permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
