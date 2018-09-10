package com.nsutanto.foodfinder.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Location implements Parcelable {

    @SerializedName("address")
    private String address;
    @SerializedName("city")
    private String city;
    @SerializedName("city_id")
    private String city_id;
    @SerializedName("latitude")
    private String latitude;
    @SerializedName("longitude")
    private String longitude;
    @SerializedName("zipcode")
    private String zipcode;

    public Location(String address, String city, String city_id, String latitude, String longitude, String zipcode) {
        this.address = address;
        this.city = city;
        this.city_id = city_id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.zipcode = zipcode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCity_id() {
        return city_id;
    }

    public void setCity_id(String city_id) {
        this.city_id = city_id;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public static final Parcelable.Creator<Location> CREATOR = new Parcelable.Creator<Location>() {

        @Override
        public Location createFromParcel(Parcel in) {
            return new Location(in);
        }

        @Override
        public Location[] newArray(int size) {
            return new Location[size];
        }
    };

    @Override
    public void writeToParcel(Parcel out, int flags) {

        out.writeString(address);
        out.writeString(city);
        out.writeString(city_id);
        out.writeString(latitude);
        out.writeString(longitude);
        out.writeString(zipcode);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    private Location(Parcel in) {
        address = in.readString();
        city = in.readString();
        city_id = in.readString();
        latitude = in.readString();
        longitude = in.readString();
        zipcode = in.readString();
    }
}
