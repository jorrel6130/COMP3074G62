package com.example.personalrestaurantguide;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements RestaurantInterface {

    ArrayList<RestaurantModel> restaurantModels = new ArrayList<>();

    int restaurantImage = R.drawable.rest_symbol;

    String[] restaurantTags = getResources().getStringArray(R.array.placeholder_tags);

    int restaurantRating = 0;

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
    }

    private void setUpRestaurantModels() {
        String[] restaurantNames = getResources().getStringArray(R.array.restaurant_names);
        String[] restaurantDescriptions = getResources().getStringArray(R.array.restaurant_names); // Placeholder; I didn't want to write 10 descriptions

        for (int i = 0; i < restaurantNames.length; i++) {
            restaurantModels.add(new RestaurantModel(restaurantNames[i], restaurantImage, restaurantDescriptions[i], restaurantTags, restaurantRating));
        }
    }

    @Override
    public void onItemClick(int position) {
        /*
        Intent intent = new Intent(MainActivity.this, FullPageActivity.class);

        intent.putExtra("NAME", restaurantModels.get(position).getRestaurantModels().getRestaurantName());
        intent.putExtra("DESCRIPTION", restaurantModels.get(position).getRestaurantModels().getRestaurantDescription());
        intent.putExtra("DESCRIPTION", restaurantModels.get(position).getRestaurantModels().getRestaurantTags());
        intent.putExtra("DESCRIPTION", restaurantModels.get(position).getRestaurantModels().getRestaurantRating());
        */
    }
}