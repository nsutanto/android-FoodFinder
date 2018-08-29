package com.nsutanto.foodfinder.model;

import com.google.gson.annotations.SerializedName;

public class RestaurantInfo {

    @SerializedName("id")
    private String id;
    @SerializedName("name")
    private String name;

    public RestaurantInfo(String id, String name) {
        this.id = id;
        this.name = name;
    }


    //private Location location;
    //private List<Ingredient> ingredients;
    //private List<Step> steps;
    //private int servings;
    //private String image;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    //public List<Ingredient> getIngredients() {
    //    return ingredients;
    //}
}
