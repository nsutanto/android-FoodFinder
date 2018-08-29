package com.nsutanto.foodfinder.task;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.nsutanto.foodfinder.apiclient.ZomatoClient;
import com.nsutanto.foodfinder.model.Restaurant;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
/*

public class RestaurantTask extends AsyncTaskLoader {

    private List<Restaurant> restaurants;
    //private String restaurants;

    public RestaurantTask(Context context) {
        super(context);
    }

    @Override
    protected void onStartLoading() {

        if (restaurants != null) {
            deliverResult(restaurants);
        } else {
            forceLoad();
        }
    }

    @Override
    public List<Restaurant> loadInBackground() {

        try {

            ZomatoClient.ZomatoService zomatoService = ZomatoClient.getRetrofitInstance().create(ZomatoClient.ZomatoService.class);
            Call<List<Restaurant>> call = zomatoService.search("Chicago", "city");
            restaurants = call.execute().body();
            return restaurants;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void deliverResult(List<Restaurant> restaurants) {
        this.restaurants = restaurants;
        super.deliverResult(restaurants);
    }
}
*/
