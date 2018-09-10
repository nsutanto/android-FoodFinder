package com.nsutanto.foodfinder.model;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import java.util.List;

@Dao
public interface RestaurantInfoDao {

    @Query("SELECT * FROM RestaurantInfo")
    LiveData<List<RestaurantInfo>> loadAllRestaurantInfo();

    @Insert
    void insertRestaurantInfo(RestaurantInfo restaurantInfo);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateRestaurantInfo(RestaurantInfo restaurantInfo);

    @Delete
    void deleteRestaurantInfo(RestaurantInfo restaurantInfo);
}