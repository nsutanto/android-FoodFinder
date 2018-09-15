package com.nsutanto.foodfinder.ui;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.location.LocationResult;
import com.nsutanto.foodfinder.R;

import butterknife.BindView;
import butterknife.ButterKnife;

import com.nsutanto.foodfinder.adapter.RestaurantAdapter;
import com.nsutanto.foodfinder.listener.IRestaurantAdapterListener;
import com.nsutanto.foodfinder.listener.IRestaurantFragmentListener;
import com.nsutanto.foodfinder.listener.IRestaurantSearchListener;
import com.nsutanto.foodfinder.model.Restaurant;
import com.nsutanto.foodfinder.task.GpsManager;
import com.nsutanto.foodfinder.task.SearchRestaurantTask;
import com.nsutanto.foodfinder.util.constant;

import java.util.ArrayList;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;


public class RestaurantLayoutFragment extends Fragment implements IRestaurantSearchListener, IRestaurantAdapterListener {

    @BindView(R.id.rv_restaurant)
    RecyclerView rv_restaurant;

    private int category;
    private Activity activity;
    private double latitude;
    private double longitude;
    private RestaurantAdapter restaurantAdapter;
    private GridLayoutManager layoutManager;
    private IRestaurantFragmentListener iRestaurantFragmentListener;


    public void setCategory(int category) {
        this.category = category;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_restaurant_layout, container, false);
        ButterKnife.bind(this, view);
        activity = getActivity();
        if (savedInstanceState != null) {
            // TODO:
        }

        initRecyclerView(view);
        fetchSearchRestaurant();
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            iRestaurantFragmentListener = (IRestaurantFragmentListener) context;
        } catch (ClassCastException ec) {
            throw new ClassCastException(context.toString()
                    + " must implement listener");
        }
    }

    public void fetchSearchRestaurant() {

        ConnectivityManager cm = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();

        if (info != null && info.isConnectedOrConnecting()) {
            new SearchRestaurantTask().execute(this);
        } else {
            showError(getResources().getString(R.string.internet_fail));
        }
    }

    private void showError(String message) {
        AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
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

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) { this.latitude = latitude; }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) { this.longitude = longitude; }

    public String getCategory() {
        if (category == constant.BREAKFAST) {
            return constant.ZOMATO_BREAKFAST_ID;
        }
        return constant.ZOMATO_DINNER_ID;
    }

    public void onPostExecute(ArrayList<Restaurant> restaurants) {
        if (restaurants != null) {
            restaurantAdapter.setRestaurants(restaurants);
        } else {
            showError(getResources().getString(R.string.fail_to_get_restaurant));
        }
    }

    public void onRestaurantClick(Restaurant restaurant) {
        iRestaurantFragmentListener.onRestaurantClick(restaurant);
    }

    private void initRecyclerView(View view) {
        int numColumn = calculateNoOfColumns(view.getContext());
        restaurantAdapter = new RestaurantAdapter(this);
        layoutManager = new GridLayoutManager(view.getContext(), numColumn, GridLayoutManager.VERTICAL, false);
        rv_restaurant.setLayoutManager(layoutManager);
        rv_restaurant.setHasFixedSize(true);

        rv_restaurant.setAdapter(restaurantAdapter);

    }

    public static int calculateNoOfColumns(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        int scalingFactor = 200;
        int noOfColumns = (int) (dpWidth / scalingFactor);
        if(noOfColumns < 2)
            noOfColumns = 2;
        return noOfColumns;
    }



    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }
}
