package com.nsutanto.foodfinder.ui;

import android.support.v4.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.nsutanto.foodfinder.R;
import com.nsutanto.foodfinder.model.AppDatabase;
import com.nsutanto.foodfinder.model.Location;
import com.nsutanto.foodfinder.model.RestaurantInfo;
import com.nsutanto.foodfinder.util.AppExecutors;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RestaurantDetailActivity extends AppCompatActivity  implements OnMapReadyCallback {

    @BindView(R.id.toolbarImage)
    ImageView toolBarImage;

    @BindView(R.id.tv_address)
    TextView tv_address;

    @BindView(R.id.tv_restaurant_name)
    TextView tv_restaurant_name;

    @BindView(R.id.iv_star)
    ImageView iv_star;

    private RestaurantInfo restaurantInfo;
    private AppDatabase appDatabase;
    private GoogleMap googleMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_detail);
        ButterKnife.bind(this);


        appDatabase = AppDatabase.getInstance(getApplicationContext());

        Intent intent = getIntent();
        restaurantInfo = intent.getParcelableExtra("restaurantInfo");

        updateUI(restaurantInfo);
        setupMapFragment();
    }

    private void setupMapFragment() {

        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;

        LatLng restaurantLocation = new LatLng(getLatitude(), getLongitude());
        googleMap.addMarker(new MarkerOptions().position(restaurantLocation).title(getRestaurantName()));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(restaurantLocation, 15));
    }

    private void updateUI(RestaurantInfo restaurantInfo) {

        String featureImageUrl = restaurantInfo.getFeatured_image();
        if (!featureImageUrl.equals("")) {
            Picasso.get()
                    .load(featureImageUrl)
                    .into(toolBarImage);
        }

        Location location = restaurantInfo.getLocation();
        String address = location.getAddress();

        tv_address.setText(getString(R.string.address) + ": " + address);
        tv_restaurant_name.setText(getRestaurantName());

        if (restaurantInfo.getFavorite() == 1) {
            fillStar();
        } else {
            unfillStar();
        }

    }

    public void onStarClicked(View view) {
        if (restaurantInfo.getFavorite() == 0) {
            fillStar();
            restaurantInfo.setFavorite(1);
            AppExecutors.getsInstance().diskIO().execute(new Runnable() {
                @Override
                public void run() {
                    appDatabase.restaurantInfoDao().insertRestaurantInfo(restaurantInfo);
                }
            });
        } else {
            AppExecutors.getsInstance().diskIO().execute(new Runnable() {
                @Override
                public void run() {
                    appDatabase.restaurantInfoDao().deleteRestaurantInfo(restaurantInfo);
                }
            });

            unfillStar();
            restaurantInfo.setFavorite(0);
        }
    }

    private Double getLongitude() {
        Double longitude = Double.parseDouble(restaurantInfo.getLocation().getLongitude());
        return longitude;
    }

    private Double getLatitude() {
        Double latitude = Double.parseDouble(restaurantInfo.getLocation().getLatitude());
        return latitude;
    }

    public String getRestaurantName() {
        return restaurantInfo.getName();
    }

    private void fillStar() {
        iv_star.setImageResource(R.drawable.ic_star_filled);
    }

    private void unfillStar() {
        iv_star.setImageResource(R.drawable.ic_star);
    }



}
