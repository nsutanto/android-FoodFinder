package com.nsutanto.foodfinder.model;


import com.google.gson.annotations.SerializedName;

public class Restaurant {

    @SerializedName("restaurant")
    private RestaurantInfo restaurantInfo;

    public RestaurantInfo getRestaurantInfo() {
        return restaurantInfo;
    }

    public void setRestaurantInfo(RestaurantInfo restaurantInfo) {
        this.restaurantInfo = restaurantInfo;
    }

    public Restaurant(RestaurantInfo restaurantInfo) {
        this.restaurantInfo = restaurantInfo;
    }
}
