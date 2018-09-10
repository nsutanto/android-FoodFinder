package com.nsutanto.foodfinder.ui;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.nsutanto.foodfinder.R;
import com.nsutanto.foodfinder.adapter.RestaurantPagerAdapter;
import com.nsutanto.foodfinder.listener.IRestaurantFragmentListener;
import com.nsutanto.foodfinder.model.Restaurant;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements IRestaurantFragmentListener {

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

        Intent intent = new Intent(this, RestaurantDetailActivity.class);
        intent.putExtra("restaurantInfo", restaurant.getRestaurantInfo());
        startActivity(intent);
    }

    private void setupPager() {

        restaurantPagerAdapter = new RestaurantPagerAdapter(getSupportFragmentManager(), this);
        vp_restaurant.setAdapter(restaurantPagerAdapter);
        tab_restaurant.setupWithViewPager(vp_restaurant);
    }
}
