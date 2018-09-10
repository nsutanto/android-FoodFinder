package com.nsutanto.foodfinder.listener;

import com.nsutanto.foodfinder.model.Restaurant;

import java.util.ArrayList;

public interface IRestaurantSearchListener {
    double getLatitude();
    double getLongitude();
    String getCategory();
    void onPostExecute(ArrayList<Restaurant> restaurants);
}
