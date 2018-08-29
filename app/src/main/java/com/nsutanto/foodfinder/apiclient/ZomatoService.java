package com.nsutanto.foodfinder.apiclient;

import com.nsutanto.foodfinder.BuildConfig;
import com.nsutanto.foodfinder.model.Restaurant;
import com.nsutanto.foodfinder.model.Search;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;


public interface ZomatoService {

    String API_KEY = BuildConfig.API_KEY;

    @Headers({
            "user-key: " + API_KEY,
            "Accept: application/json"
    })
    @GET("/api/v2.1/search")
    Call<Search> search(
            @Query("entity_id") String entityId,
            @Query("entity_type") String entityType);
}