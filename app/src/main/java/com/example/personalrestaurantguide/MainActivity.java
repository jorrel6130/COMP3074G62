// Jorrel Tigbayan
// 101329925

package com.example.personalrestaurantguide;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements RestaurantInterface {

    // Declaring variables that will be used in multiple methods outside
    ArrayList<RestaurantModel> restaurantModels;
    ArrayList<RestaurantModel> filteredList;
    RestaurantViewAdapter adapter;
    AlertDialog.Builder builder;
    private SearchView searchView;
    String searchQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Pull restaurant list from SharedPrefs; if none exists then a new one is made.
        restaurantModels = PrefConfig.readListFromPref(this);
        if (restaurantModels == null) {
            restaurantModels = new ArrayList<>();
        }

        // Setting up RecyclerView
        RecyclerView recyclerView  = findViewById(R.id.restaurantRecyclerView);
        adapter = new RestaurantViewAdapter(this, restaurantModels, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Setting up SearchView
        searchView = findViewById(R.id.searchView);
        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(MainActivity.this, searchQuery, Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchQuery = newText;
                filterList(newText);
                return true;
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Adding toolbar for navigation to create activity
        Toolbar navbar = findViewById(R.id.navbar);
        setSupportActionBar(navbar);

        // Instantiating AlertDialog builder for delete function
        builder = new AlertDialog.Builder(this);
    }

    private void filterList(String newText) {
        newText = newText.trim();
        filteredList = new ArrayList<>();
        for (RestaurantModel restaurantModel : restaurantModels) {
            if (restaurantModel.getName().trim().toLowerCase().contains(newText.toLowerCase())) {
                filteredList.add(restaurantModel);
                continue;
            }
            for (String tagQuery : restaurantModel.getTags()) {
                if (tagQuery.trim().toLowerCase().contains(newText.toLowerCase())) {
                    filteredList.add(restaurantModel);
                }
            }
        }

        adapter.setFilteredList(filteredList);
    }

    // Tapping one of the restaurant cards will open their info screen
    @Override
    public void onItemClick(int position) {
        int realPosition = getRealPosition(position);
        Intent intent = new Intent(MainActivity.this, RestaurantInfoActivity.class);
        intent.putExtra("position", realPosition); // Pushing position through intent to retrieve data via list in the info activity
        startActivity(intent);
    }

    // Long tap will trigger a dialog for deletion
    @Override
    public void onItemLongClick(int position) {
        int realPosition = getRealPosition(position);
        String restName = restaurantModels.get(realPosition).getName();
        builder.setTitle("Delete")
                .setMessage("Do you want to delete " + restName + "?")
                .setCancelable(true)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // If Yes, restaurant is removed from list and adapter is updated
                        restaurantModels.remove(realPosition);
                        PrefConfig.writeListInPref(getApplicationContext(), restaurantModels);
                        adapter.notifyItemRemoved(realPosition);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                })
                .show();
    }

    // Inflates navbar with "create restaurant" button
    public boolean onCreateOptionsMenu (Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    // If create button is tapped, starts creation activity
    public boolean onOptionsItemSelected (@NonNull MenuItem item) {
        if (item.getItemId() == R.id.add_icon) {
            Intent intent = new Intent(this, RestaurantCreationActivity.class);
            startActivity(intent);
        }
        return true;
    }

    public int getRealPosition(int position) {
        int realPosition = position;
        RestaurantModel currentRestaurant;
        if (searchQuery != "" && searchQuery != null) {
            RestaurantModel selectedRestaurant = filteredList.get(position);
            for (int i = 0; i < (restaurantModels.size()); i++) {
                currentRestaurant = restaurantModels.get(i);
                if (selectedRestaurant.equals(currentRestaurant)) {
                    realPosition = i;
                }
            }
        }
        return realPosition;
    }
}