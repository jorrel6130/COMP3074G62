package com.example.personalrestaurantguide;

import android.content.Intent;
import android.health.connect.datatypes.AppInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements RestaurantInterface {

    ArrayList<RestaurantModel> restaurantModels = new ArrayList<>();

    int restaurantImage = R.drawable.rest_symbol;

    int restaurantRating = 0;

    String restaurantAddress = "Placeholder";

    private Toolbar navbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.restaurantRecyclerView);

        setUpRestaurantModels();

        RestaurantViewAdapter adapter = new RestaurantViewAdapter(this, restaurantModels, this);

        recyclerView.setAdapter(adapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        navbar = findViewById(R.id.navbar);
        setSupportActionBar(navbar);
    }

    private void setUpRestaurantModels() {
        String[] restaurantNames = getResources().getStringArray(R.array.restaurant_names);
        String[] restaurantDescriptions = getResources().getStringArray(R.array.restaurant_names);// Placeholder; I didn't want to write 10 descriptions
        String[] restaurantTags = getResources().getStringArray(R.array.placeholder_tags);

        for (int i = 0; i < restaurantNames.length; i++) {
            restaurantModels.add(new RestaurantModel(restaurantNames[i], restaurantImage, restaurantAddress, restaurantDescriptions[i], restaurantTags, restaurantRating));
        }
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(MainActivity.this, RestaurantInfoActivity.class);

        intent.putExtra("name", restaurantModels.get(position).getRestaurantName());
        intent.putExtra("address", restaurantModels.get(position).getRestaurantDescription());
        intent.putExtra("notes", restaurantModels.get(position).getRestaurantDescription());
        intent.putExtra("tags", restaurantModels.get(position).getRestaurantTags());
        intent.putExtra("rating", restaurantModels.get(position).getRestaurantRating());

        startActivity(intent);
    }

    public boolean onCreateOptionsMenu (Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected (@NonNull MenuItem item) {
        if (item.getItemId() == R.id.add_icon) {
            Intent intent = new Intent(this, RestaurantCreationActivity.class);
            startActivity(intent);
        }
        return true;
    }
}