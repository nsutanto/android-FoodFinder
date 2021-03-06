package com.nsutanto.foodfinder.adapter;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nsutanto.foodfinder.R;
import com.nsutanto.foodfinder.listener.IRestaurantAdapterListener;
import com.nsutanto.foodfinder.model.Restaurant;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.ViewHolder> {

    private List<Restaurant> restaurants = new ArrayList<>();
    private Context context;
    private static IRestaurantAdapterListener restaurantListener;


    public RestaurantAdapter(IRestaurantAdapterListener restaurantListener) {
        this.restaurantListener = restaurantListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.iv_restaurant)
        ImageView iv_restaurant;

        @BindView(R.id.tv_restaurant_name)
        TextView tv_restaurant;

        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
            v.setOnClickListener(this);
        }

        public void bind(Restaurant restaurant) {
            tv_restaurant.setText(restaurant.getRestaurantInfo().getName());
            String posterPath = restaurant.getRestaurantInfo().getThumb();
            if (posterPath.equals("") == false) {
                Picasso.get()
                        .load(posterPath)
                        .into(iv_restaurant);
            }
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            restaurantListener.onRestaurantClick(restaurants.get(position));
        }
    }

    @Override
    public RestaurantAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.restaurant_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Restaurant restaurant = restaurants.get(position);
        holder.bind(restaurant);
    }

    @Override
    public int getItemCount() {
        if (restaurants != null) {
            return restaurants.size();
        } else {
            return 0;
        }
    }

    public void setRestaurants(List<Restaurant> restaurants) {
        if (restaurants != null) {

            for (Iterator<Restaurant> iter = restaurants.listIterator(); iter.hasNext(); ) {
                Restaurant restaurant = iter.next();
                if (restaurant.getRestaurantInfo().getThumb().equals("")) {
                    iter.remove();
                }
            }

            this.restaurants = restaurants;
            notifyDataSetChanged();
        }
    }
}