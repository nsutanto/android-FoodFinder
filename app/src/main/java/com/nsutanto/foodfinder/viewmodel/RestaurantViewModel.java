package com.nsutanto.foodfinder.viewmodel;

import android.arch.lifecycle.AndroidViewModel;
import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import com.nsutanto.foodfinder.model.AppDatabase;
import com.nsutanto.foodfinder.model.RestaurantInfo;

import java.util.List;

public class RestaurantViewModel extends AndroidViewModel {

    private LiveData<List<RestaurantInfo>> restaurantInfos;

    public RestaurantViewModel(@NonNull Application application) {
        super(application);
        AppDatabase appDatabase = AppDatabase.getInstance(this.getApplication());
        restaurantInfos = appDatabase.restaurantInfoDao().loadAllRestaurantInfo();
    }

    public LiveData<List<RestaurantInfo>> getRestaurantInfos() {
        return restaurantInfos;
    }
}