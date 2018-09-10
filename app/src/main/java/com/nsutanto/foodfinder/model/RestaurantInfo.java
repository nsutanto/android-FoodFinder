package com.nsutanto.foodfinder.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "RestaurantInfo")
public class RestaurantInfo implements Parcelable {

    @PrimaryKey
    @NonNull
    @SerializedName("id")
    private String id;

    @ColumnInfo(name = "name")
    @SerializedName("name")
    private String name;

    @Embedded
    @SerializedName("location")
    private Location location;

    @ColumnInfo(name = "cuisines")
    @SerializedName("cuisines")
    private String cuisines;

    @ColumnInfo(name = "thumb")
    @SerializedName("thumb")
    private String thumb;

    @ColumnInfo(name = "featured_image")
    @SerializedName("featured_image")
    private String featured_image;


    public RestaurantInfo(@NonNull String id, String name, Location location, String cuisines, String thumb, String featured_image) {
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

    public static final Parcelable.Creator<RestaurantInfo> CREATOR = new Parcelable.Creator<RestaurantInfo>() {

        @Override
        public RestaurantInfo createFromParcel(Parcel in) {
            return new RestaurantInfo(in);
        }

        @Override
        public RestaurantInfo[] newArray(int size) {
            return new RestaurantInfo[size];
        }
    };

    @Override
    public void writeToParcel(Parcel out, int flags) {

        out.writeString(id);
        out.writeString(name);
        out.writeValue(location);
        out.writeString(cuisines);
        out.writeString(thumb);
        out.writeString(featured_image);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    private RestaurantInfo(Parcel in) {
        id = in.readString();
        name = in.readString();
        location = (Location) in.readValue(Location.class.getClassLoader());
        cuisines = in.readString();
        thumb = in.readString();
        featured_image = in.readString();
    }
}
