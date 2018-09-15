package com.nsutanto.foodfinder.ui;

import android.Manifest;
import android.annotation.TargetApi;
import android.appwidget.AppWidgetManager;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RemoteViews;

import com.google.android.gms.location.LocationResult;
import com.nsutanto.foodfinder.R;
import com.nsutanto.foodfinder.adapter.RestaurantPagerAdapter;
import com.nsutanto.foodfinder.listener.IRestaurantFragmentListener;
import com.nsutanto.foodfinder.listener.IRestaurantInfoFragmentListener;
import com.nsutanto.foodfinder.model.Restaurant;
import com.nsutanto.foodfinder.model.RestaurantInfo;
import com.nsutanto.foodfinder.task.GpsManager;
import com.nsutanto.foodfinder.task.SearchRestaurantTask;
import com.nsutanto.foodfinder.viewmodel.RestaurantViewModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class MainActivity extends AppCompatActivity implements GpsManager.Callback, IRestaurantFragmentListener, IRestaurantInfoFragmentListener {

    @BindView(R.id.pager_restaurant)
    ViewPager vp_restaurant;

    @BindView(R.id.tab_restaurant)
    TabLayout tab_restaurant;

    private RestaurantPagerAdapter restaurantPagerAdapter;
    private List<RestaurantInfo> favoriteRestaurants = new ArrayList<>();
    private boolean isWidget = false;
    private int widgetID;
    private static final int PERMISSION_REQUEST_FINE_LOCATION = 1;
    private GpsManager gpsManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        gpsManager = new GpsManager();

        Timber.plant(new Timber.DebugTree());

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

    private void setupPager(Double latitude, Double longitude) {

        restaurantPagerAdapter = new RestaurantPagerAdapter(getSupportFragmentManager(), this, latitude, longitude);
        vp_restaurant.setAdapter(restaurantPagerAdapter);
        tab_restaurant.setupWithViewPager(vp_restaurant);
    }

    private void startGps() {

        // check if user gave permissions, otherwise ask via dialog
        if (!checkPermission()) {
            getLocationPermissions();
            return;
        }

        gpsManager.start(getApplicationContext(), this);
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void getLocationPermissions() {
        requestPermissions(new String[] {Manifest.permission.ACCESS_FINE_LOCATION},
                PERMISSION_REQUEST_FINE_LOCATION);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (permissions.length > 0 && grantResults.length > 0) {
            switch (requestCode) {
                case PERMISSION_REQUEST_FINE_LOCATION: {
                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        startGps();
                    } else {
                        showError(getResources().getString(R.string.gps_not_granted));
                    }
                    break;
                }
                default:
                    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            }
        }
    }

    private boolean checkPermission() {
        return isGranted(ActivityCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION)) &&
                isGranted(ActivityCompat.checkSelfPermission(this, ACCESS_COARSE_LOCATION));
    }

    private boolean isGranted(int permission) {
        return permission == PackageManager.PERMISSION_GRANTED;
    }

    private void showError(String message) {
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle(getResources().getString(R.string.alert));
        alertDialog.setMessage(message);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, getResources().getString(R.string.ok),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

    @Override
    public void onLocationResult(LocationResult locationResult) {
        Location location = locationResult.getLastLocation();
        setupPager(location.getLatitude(), location.getLongitude());
    }

    @Override
    public void onStart() {
        super.onStart();
        startGps();
    }
}
