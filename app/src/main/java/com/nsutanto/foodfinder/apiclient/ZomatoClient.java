package com.nsutanto.foodfinder.apiclient;

import com.nsutanto.foodfinder.model.Restaurant;
import com.nsutanto.foodfinder.model.Search;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;

public class ZomatoClient {

    private static Retrofit retrofit;
    private static final String BASE_URL = "https://developers.zomato.com";

    private static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static synchronized ArrayList<Restaurant> GetRestaurant(Double latitude, Double longitude, String category) {
        Timber.v(">> Zomato Get Restaurant. Latitude: " + latitude + " Longitude: " + longitude + " Category: " + category);
        ZomatoService service = getRetrofitInstance().create(ZomatoService.class);
        Call<Search> call = service.search(latitude, longitude, category);
        ArrayList<Restaurant> restaurants = new ArrayList<>();
        try {
            Search search = call.execute().body();
            restaurants = search.getRestaurants();
        } catch (IOException ex) {
            Timber.d("Error get restaurant: " + ex.getMessage());

        }
        Timber.v("<< Zomato Get Restaurant.");
        return restaurants;
    }
}