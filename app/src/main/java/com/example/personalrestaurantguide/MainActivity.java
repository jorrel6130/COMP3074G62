// Jorrel Tigbayan
// 101329925

package com.example.personalrestaurantguide;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements RestaurantInterface {

    // Delcaring variables that will be used in multiple methods outside
    ArrayList<RestaurantModel> restaurantModels;
    RestaurantViewAdapter adapter;
    AlertDialog.Builder builder;

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

    // Tapping one of the restaurant cards will open their info screen
    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(MainActivity.this, RestaurantInfoActivity.class);
        intent.putExtra("position", position); // Pushing position through intent to retrieve data via list in the info activity
        startActivity(intent);
    }

    // Long tap will trigger a dialog for deletion
    @Override
    public void onItemLongClick(int position) {
        String restName = restaurantModels.get(position).getName();
        builder.setTitle("Delete")
                .setMessage("Do you want to delete " + restName + "?")
                .setCancelable(true)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // If Yes, restaurant is removed from list and adapter is updated
                        restaurantModels.remove(position);
                        PrefConfig.writeListInPref(getApplicationContext(), restaurantModels);
                        adapter.notifyItemRemoved(position);
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
}