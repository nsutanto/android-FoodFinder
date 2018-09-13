package com.nsutanto.foodfinder.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.nsutanto.foodfinder.R;

import butterknife.ButterKnife;


public class MapFragment extends Fragment implements OnMapReadyCallback {
    private GoogleMap googleMap;

    public MapFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;

        //Double latitude = Double.parseDouble(restaurantInfo.getLocation().getLatitude());
        //Double longitude = Double.parseDouble(restaurantInfo.getLocation().getLongitude());

        //LatLng restaurantLocation = new LatLng(latitude, longitude);
        LatLng restaurantLocation = new LatLng(100, 100);
        //googleMap.addMarker(new MarkerOptions().position(restaurantLocation).title(restaurantInfo.getName()));
        googleMap.addMarker(new MarkerOptions().position(restaurantLocation).title("test"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(restaurantLocation));
    }
}