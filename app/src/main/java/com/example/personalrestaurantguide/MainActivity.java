package com.example.personalrestaurantguide;

import android.content.DialogInterface;
import android.content.Intent;
import android.health.connect.datatypes.AppInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
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

    ArrayList<RestaurantModel> restaurantModels;
    RestaurantViewAdapter adapter;
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView  = findViewById(R.id.restaurantRecyclerView);

        restaurantModels = PrefConfig.readListFromPref(this);
        if (restaurantModels == null) {
            restaurantModels = new ArrayList<>();
        }

        adapter = new RestaurantViewAdapter(this, restaurantModels, this);

        recyclerView.setAdapter(adapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Toolbar navbar = findViewById(R.id.navbar);
        setSupportActionBar(navbar);

        builder = new AlertDialog.Builder(this);
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(MainActivity.this, RestaurantInfoActivity.class);
        intent.putExtra("position", position);
        startActivity(intent);
    }

    @Override
    public void onItemLongClick(int position) {
        String restName = restaurantModels.get(position).getName();
        builder.setTitle("Delete")
                .setMessage("Do you want to delete " + restName + "?")
                .setCancelable(true)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
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