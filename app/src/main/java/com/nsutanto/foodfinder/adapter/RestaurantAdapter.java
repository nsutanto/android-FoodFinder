package com.nsutanto.foodfinder.adapter;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nsutanto.foodfinder.R;
import com.nsutanto.foodfinder.listener.IRestaurantListener;
import com.nsutanto.foodfinder.model.Restaurant;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.ViewHolder> {

    private List<Restaurant> restaurants = new ArrayList<>();
    private Context context;
    private static IRestaurantListener restaurantListener;


    public RestaurantAdapter(IRestaurantListener restaurantListener) {
        this.restaurantListener = restaurantListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        //@BindView(R.id.tv_recipe) TextView recipeTitle;

        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
            v.setOnClickListener(this);
        }

        public void bind(Restaurant restaurant) {

        }

        @Override
        public void onClick(View view) {
            //int position = getAdapterPosition();
            //recipeListener.OnRecipeClick(recipes.get(position));
        }
    }

    @Override
    public RestaurantAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        //View view = inflater.inflate(R.layout.recipe_item, parent, false);
        //return new ViewHolder(view);
        return null;
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
            this.restaurants = restaurants;
            notifyDataSetChanged();
        }
    }
}