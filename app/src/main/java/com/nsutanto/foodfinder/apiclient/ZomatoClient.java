package com.nsutanto.foodfinder.apiclient;

import com.nsutanto.foodfinder.model.Restaurant;
import com.nsutanto.foodfinder.model.Search;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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

    public static void GetRestaurant(Double latitude, Double longitude, String category) {
        ZomatoService service = getRetrofitInstance().create(ZomatoService.class);
        Call<Search> call = service.search(latitude, longitude, category);
        call.enqueue(new Callback<Search>() {
            @Override
            public void onResponse(Call<Search> call, Response<Search> response) {
                Search search = response.body();
                List<Restaurant> restaurants = search.getRestaurants();
            }

            @Override
            public void onFailure(Call<Search> call, Throwable t) {
                String url = call.request().url().toString();
                String header = call.request().headers().toString();

            }
        });
    }



}