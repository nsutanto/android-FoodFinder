package com.nsutanto.foodfinder.ui;

import android.app.FragmentManager;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.nsutanto.foodfinder.R;
import com.nsutanto.foodfinder.adapter.RestaurantPagerAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

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

    private void setupPager() {

        restaurantPagerAdapter = new RestaurantPagerAdapter(getSupportFragmentManager(), this);
        vp_restaurant.setAdapter(restaurantPagerAdapter);
        tab_restaurant.setupWithViewPager(vp_restaurant);

        /*
        FragmentManager fm = getFragmentManager();

        fm.beginTransaction()
                .add(R.id.recipe_container, recipeFragment)
                .commit();
                */
    }
}
