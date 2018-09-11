package com.nsutanto.foodfinder.ui;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.nsutanto.foodfinder.R;
import com.nsutanto.foodfinder.adapter.RestaurantPagerAdapter;
import com.nsutanto.foodfinder.listener.IRestaurantFragmentListener;
import com.nsutanto.foodfinder.listener.IRestaurantInfoFragmentListener;
import com.nsutanto.foodfinder.model.Restaurant;
import com.nsutanto.foodfinder.model.RestaurantInfo;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements IRestaurantFragmentListener, IRestaurantInfoFragmentListener {

    @BindView(R.id.pager_restaurant)
    ViewPager vp_restaurant;

    @BindView(R.id.tab_restaurant)
    TabLayout tab_restaurant;

    private RestaurantPagerAdapter restaurantPagerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setupPager();
    }

    public void onRestaurantClick(Restaurant restaurant) {

       openRestaurantDetail(restaurant.getRestaurantInfo());
    }

    public void onRestaurantInfoClick(RestaurantInfo restaurantInfo) {
        openRestaurantDetail(restaurantInfo);
    }

    private void openRestaurantDetail(RestaurantInfo restaurantInfo) {
        Intent intent = new Intent(this, RestaurantDetailActivity.class);
        intent.putExtra("restaurantInfo", restaurantInfo);
        startActivity(intent);
    }

    private void setupPager() {

        restaurantPagerAdapter = new RestaurantPagerAdapter(getSupportFragmentManager(), this);
        vp_restaurant.setAdapter(restaurantPagerAdapter);
        tab_restaurant.setupWithViewPager(vp_restaurant);
    }
}
