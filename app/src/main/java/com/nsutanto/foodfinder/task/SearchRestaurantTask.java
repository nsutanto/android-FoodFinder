package com.nsutanto.foodfinder.task;

import android.os.AsyncTask;

import com.nsutanto.foodfinder.apiclient.ZomatoClient;
import com.nsutanto.foodfinder.listener.IRestaurantSearchListener;
import com.nsutanto.foodfinder.model.Restaurant;

import java.util.ArrayList;

public class SearchRestaurantTask extends AsyncTask<IRestaurantSearchListener, Void, ArrayList<Restaurant>> {

    private IRestaurantSearchListener taskListener;

    @Override
    protected void onPreExecute() {

        super.onPreExecute();
    }

    @Override
    protected ArrayList<Restaurant> doInBackground(IRestaurantSearchListener... taskListeners) {

        taskListener = taskListeners[0];

        double latitude = taskListener.getLatitude();
        double longitude = taskListener.getLongitude();
        String category = taskListener.getCategory();

        try {
            ArrayList<Restaurant> restaurants = ZomatoClient.GetRestaurant(latitude, longitude, category);
            return restaurants;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(ArrayList<Restaurant> restaurants) {
        taskListener.onPostExecute(restaurants);
    }
}
