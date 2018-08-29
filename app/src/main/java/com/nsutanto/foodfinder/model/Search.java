package com.nsutanto.foodfinder.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Search {

    @SerializedName("restaurants")
    private ArrayList<Restaurant> restaurants;
    @SerializedName("results_found")
    private int results_found;

    public ArrayList<Restaurant>  getRestaurants() {
        return restaurants;
    }

    public void setRestaurants(ArrayList<Restaurant> restaurants) {
        this.restaurants = restaurants;
    }

    public int getResults_found() { return results_found; }

    public void setResults_found(int results_found) {
        this.results_found = results_found;
    }

}