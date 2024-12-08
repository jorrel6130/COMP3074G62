// Jorrel Tigbayan
// 101329925

package com.example.personalrestaurantguide;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class RestaurantViewAdapter extends RecyclerView.Adapter<RestaurantViewAdapter.MyViewHolder>{
    private final RestaurantInterface restaurantInterface;

    Context context;
    ArrayList<RestaurantModel> restaurantModels;

    public RestaurantViewAdapter(Context context, ArrayList<RestaurantModel> restaurantModels, RestaurantInterface restaurantInterface) {
        this.context = context;
        this.restaurantModels = restaurantModels;
        this.restaurantInterface = restaurantInterface;
    }

    @NonNull
    @Override
    public RestaurantViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.restaurant_row, parent, false);

        return new RestaurantViewAdapter.MyViewHolder(view, restaurantInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantViewAdapter.MyViewHolder holder, int position) {

        holder.imageView.setImageResource(restaurantModels.get(position).getImage());
        holder.restaurantName.setText(restaurantModels.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return restaurantModels.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView restaurantName;

        public MyViewHolder(@NonNull View itemView, RestaurantInterface restaurantInterface) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageView);
            restaurantName = itemView.findViewById(R.id.textView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (restaurantInterface != null) {
                        int position = getAdapterPosition();

                        if (position != RecyclerView.NO_POSITION) {
                            restaurantInterface.onItemClick(position);
                        }
                    }
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    if (restaurantInterface != null) {
                        int position = getAdapterPosition();

                        if (position != RecyclerView.NO_POSITION) {
                            restaurantInterface.onItemLongClick(position);
                        }
                    }
                    return true;
                }
            });
        }
    }

    public void setFilteredList(ArrayList<RestaurantModel> filteredList) {
        this.restaurantModels = filteredList;
        notifyDataSetChanged();
    }
}
