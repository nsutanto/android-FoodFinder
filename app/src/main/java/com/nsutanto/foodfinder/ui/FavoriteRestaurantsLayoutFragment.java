package com.nsutanto.foodfinder.ui;

import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nsutanto.foodfinder.R;
import com.nsutanto.foodfinder.adapter.RestaurantInfoAdapter;
import com.nsutanto.foodfinder.listener.IRestaurantInfoAdapterListener;
import com.nsutanto.foodfinder.listener.IRestaurantInfoFragmentListener;
import com.nsutanto.foodfinder.model.RestaurantInfo;
import com.nsutanto.foodfinder.viewmodel.RestaurantViewModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FavoriteRestaurantsLayoutFragment extends Fragment implements IRestaurantInfoAdapterListener {

    @BindView(R.id.rv_restaurant)
    RecyclerView rv_restaurant;

    private Activity activity;
    private RestaurantInfoAdapter restaurantInfoAdapter;
    private GridLayoutManager layoutManager;
    private IRestaurantInfoFragmentListener iRestaurantInfoFragmentListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_restaurant_layout, container, false);
        ButterKnife.bind(this, view);

        activity = getActivity();
        if (savedInstanceState != null) {
            // TODO:
        }

        initRecyclerView(view);
        setupFavoriteRestaurantsVM();
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            iRestaurantInfoFragmentListener = (IRestaurantInfoFragmentListener) context;
        } catch (ClassCastException ec) {
            throw new ClassCastException(context.toString()
                    + " must implement listener");
        }
    }

    private void initRecyclerView(View view) {
        int numColumn = calculateNoOfColumns(view.getContext());
        restaurantInfoAdapter = new RestaurantInfoAdapter(this);
        layoutManager = new GridLayoutManager(view.getContext(), numColumn, GridLayoutManager.VERTICAL, false);
        rv_restaurant.setLayoutManager(layoutManager);
        rv_restaurant.setHasFixedSize(true);

        rv_restaurant.setAdapter(restaurantInfoAdapter);

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

    public void onRestaurantClick(RestaurantInfo restaurantInfo) {
        iRestaurantInfoFragmentListener.onRestaurantInfoClick(restaurantInfo);
    }

    private void setupFavoriteRestaurantsVM() {

        RestaurantViewModel restaurantVM = ViewModelProviders.of(this).get(RestaurantViewModel.class);
        restaurantVM.getRestaurantInfos().observe(this, new Observer<List<RestaurantInfo>>() {
            @Override
            public void onChanged(@Nullable List<RestaurantInfo> restaurantInfos) {
                restaurantInfoAdapter.setRestaurants(restaurantInfos);
            }
        });
    }
}