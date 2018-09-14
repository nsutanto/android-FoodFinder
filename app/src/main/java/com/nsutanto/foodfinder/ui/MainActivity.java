package com.nsutanto.foodfinder.ui;

import android.appwidget.AppWidgetManager;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RemoteViews;

import com.nsutanto.foodfinder.R;
import com.nsutanto.foodfinder.adapter.RestaurantPagerAdapter;
import com.nsutanto.foodfinder.listener.IRestaurantFragmentListener;
import com.nsutanto.foodfinder.listener.IRestaurantInfoFragmentListener;
import com.nsutanto.foodfinder.model.Restaurant;
import com.nsutanto.foodfinder.model.RestaurantInfo;
import com.nsutanto.foodfinder.viewmodel.RestaurantViewModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity implements IRestaurantFragmentListener, IRestaurantInfoFragmentListener {

    @BindView(R.id.pager_restaurant)
    ViewPager vp_restaurant;

    @BindView(R.id.tab_restaurant)
    TabLayout tab_restaurant;

    private RestaurantPagerAdapter restaurantPagerAdapter;
    private List<RestaurantInfo> favoriteRestaurants = new ArrayList<>();
    private boolean isWidget = false;
    private int widgetID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        Timber.plant(new Timber.DebugTree());

        setupPager();
        setupFavoriteRestaurantsVM();
        initWidget();
    }

    public void onRestaurantClick(Restaurant restaurant) {

       openRestaurantDetail(restaurant.getRestaurantInfo());
    }

    public void onRestaurantInfoClick(RestaurantInfo restaurantInfo) {
        openRestaurantDetail(restaurantInfo);
    }

    private void openRestaurantDetail(RestaurantInfo restaurantInfo) {
        if (isInFavoriteList(restaurantInfo)) {
            restaurantInfo.setFavorite(1);
        }

        if (isWidget) {
            setupWidget(restaurantInfo);
        }
        else {
            Intent intent = new Intent(this, RestaurantDetailActivity.class);
            intent.putExtra("restaurantInfo", restaurantInfo);
            startActivity(intent);
        }
    }

    private boolean isInFavoriteList(RestaurantInfo restaurantInfo) {
        boolean isFavorite = false;

        for (RestaurantInfo favoriteRestaurant: favoriteRestaurants) {
            if (restaurantInfo.getId().equals(favoriteRestaurant.getId())) {
                isFavorite = true;
                break;
            }
        }
        return isFavorite;
    }

    private void setupFavoriteRestaurantsVM() {

        RestaurantViewModel restaurantVM = ViewModelProviders.of(this).get(RestaurantViewModel.class);
        restaurantVM.getRestaurantInfos().observe(this, new Observer<List<RestaurantInfo>>() {
            @Override
            public void onChanged(@Nullable List<RestaurantInfo> restaurantInfos) {
                favoriteRestaurants = restaurantInfos;
            }
        });
    }

    private void initWidget() {
        if (getIntent().getExtras() != null) {
            if(getIntent().getExtras().containsKey(AppWidgetManager.EXTRA_APPWIDGET_ID)) {
                isWidget = true;
                widgetID = getIntent().getExtras().getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
            }
        }
    }

    private void setupWidget(RestaurantInfo restaurantInfo)
    {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);

        RemoteViews views = new RemoteViews(this.getPackageName(), R.layout.food_finder_widget_provider);
        views.setTextViewText(R.id.widget_tv_restaurant_name, restaurantInfo.getName());
        views.setTextViewText(R.id.widget_tv_address, restaurantInfo.getLocation().getAddress());


        appWidgetManager.updateAppWidget(widgetID, views);

        Intent intent = new Intent();
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetID);
        setResult(RESULT_OK, intent);
        finish();
    }



    private void setupPager() {

        restaurantPagerAdapter = new RestaurantPagerAdapter(getSupportFragmentManager(), this);
        vp_restaurant.setAdapter(restaurantPagerAdapter);
        tab_restaurant.setupWithViewPager(vp_restaurant);
    }
}
