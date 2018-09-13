package com.nsutanto.foodfinder.ui;

import android.support.v4.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.nsutanto.foodfinder.R;
import com.nsutanto.foodfinder.model.AppDatabase;
import com.nsutanto.foodfinder.model.Location;
import com.nsutanto.foodfinder.model.RestaurantInfo;
import com.nsutanto.foodfinder.util.AppExecutors;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RestaurantDetailActivity extends AppCompatActivity {

    @BindView(R.id.toolbarImage)
    ImageView toolBarImage;

    @BindView(R.id.tv_address)
    TextView tv_address;

    @BindView(R.id.iv_star)
    ImageView iv_star;

    private RestaurantInfo restaurantInfo;
    private AppDatabase appDatabase;
    private MapFragment mapFragment = new MapFragment();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_detail);
        ButterKnife.bind(this);


        appDatabase = AppDatabase.getInstance(getApplicationContext());

        Intent intent = getIntent();
        restaurantInfo = intent.getParcelableExtra("restaurantInfo");

        updateUI(restaurantInfo);
        setupFragment();
    }

    private void setupFragment() {
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction()
                .add(R.id.map_container, mapFragment)
                .commit();
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

        tv_address.setText(R.string.address + ": " + address);

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

    private void fillStar() {
        iv_star.setImageResource(R.drawable.ic_star_filled);
    }

    private void unfillStar() {
        iv_star.setImageResource(R.drawable.ic_star);
    }



}
