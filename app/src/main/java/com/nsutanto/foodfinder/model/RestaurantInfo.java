package com.nsutanto.foodfinder.model;

import com.google.gson.annotations.SerializedName;

public class RestaurantInfo {

    @SerializedName("id")
    private String id;
    @SerializedName("name")
    private String name;
    @SerializedName("location")
    private Location location;
    @SerializedName("cuisines")
    private String cuisines;
    @SerializedName("thumb")
    private String thumb;
    @SerializedName("featured_image")
    private String featured_image;


    public RestaurantInfo(String id, String name, Location location, String cuisines, String thumb, String featured_image) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.cuisines = cuisines;
        this.thumb = thumb;
        this.featured_image = featured_image;
    }

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

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getCuisines() {
        return cuisines;
    }

    public void setCuisines(String cuisines) {
        this.cuisines = cuisines;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getFeatured_image() {
        return featured_image;
    }

    public void setFeatured_image(String featured_image) {
        this.featured_image = featured_image;
    }
}
