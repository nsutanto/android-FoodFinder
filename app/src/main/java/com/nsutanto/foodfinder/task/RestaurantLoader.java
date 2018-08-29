package com.nsutanto.foodfinder.task;

import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nsutanto.foodfinder.adapter.RestaurantAdapter;
import com.nsutanto.foodfinder.model.Restaurant;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/*public class RestaurantLoader implements LoaderManager.LoaderCallbacks<String> {

    private List<Restaurant> restaurants;
    private RestaurantAdapter restaurantAdapter;

    public RestaurantLoader(RestaurantAdapter restaurantAdapter) {
        this.restaurantAdapter = restaurantAdapter;
    }

    @Override
    public Loader<String> onCreateLoader(int id, final Bundle args) {




    }


    @Override
    public void onLoadFinished(Loader<String> loader, String data) {

        if (data != null) {
            Type restaurantsType = new TypeToken<ArrayList<Restaurant>>(){}.getType();
            restaurants = new Gson().fromJson(data, restaurantsType);
            restaurantAdapter.setRestaurants(restaurants);
        }
    }

    @Override
    public void onLoaderReset(Loader<String> loader) {
        *//*
         * We aren't using this method in our example application, but we are required to Override
         * it to implement the LoaderCallbacks<String> interface
         *//*
    }
}*/
