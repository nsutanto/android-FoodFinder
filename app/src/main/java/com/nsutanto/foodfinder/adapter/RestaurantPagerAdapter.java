package com.nsutanto.foodfinder.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.nsutanto.foodfinder.R;
import com.nsutanto.foodfinder.ui.FavoriteRestaurantsLayoutFragment;
import com.nsutanto.foodfinder.ui.RestaurantLayoutFragment;
import com.nsutanto.foodfinder.util.constant;

import java.util.ArrayList;
import java.util.List;

public class RestaurantPagerAdapter extends FragmentStatePagerAdapter {

    private final List<Fragment> fragmentList = new ArrayList<>();
    private static final int NUM_PAGES = 3;
    private Context context;

    public RestaurantPagerAdapter(FragmentManager fm, Context context, double latitude, double longitude) {
        super(fm);
        this.context = context;

        RestaurantLayoutFragment breakfastFragment = new RestaurantLayoutFragment();
        breakfastFragment.setCategory(constant.BREAKFAST);
        breakfastFragment.setLatitude(latitude);
        breakfastFragment.setLongitude(longitude);

        RestaurantLayoutFragment dinnerFragment = new RestaurantLayoutFragment();
        dinnerFragment.setCategory(constant.DINNER);
        dinnerFragment.setLatitude(latitude);
        dinnerFragment.setLongitude(longitude);

        FavoriteRestaurantsLayoutFragment favoriteFragment = new FavoriteRestaurantsLayoutFragment();

        fragmentList.add(breakfastFragment);
        fragmentList.add(dinnerFragment);
        fragmentList.add(favoriteFragment);
    }

    @Override
    public Fragment getItem(int position) {

        return fragmentList.get(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title;
        switch (position) {
            case constant.BREAKFAST:
                title = context.getString(R.string.breakfast);
                break;
            case constant.DINNER:
                title = context.getString(R.string.dinner);
                break;
            case constant.FAVORITE:
                title =  context.getString(R.string.favorite);
                break;
            default:
                title = "";
        }
        return title;
    }


    @Override
    public int getCount() {
        return NUM_PAGES;
    }
}