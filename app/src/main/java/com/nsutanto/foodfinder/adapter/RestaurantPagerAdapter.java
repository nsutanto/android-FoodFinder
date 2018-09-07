package com.nsutanto.foodfinder.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.nsutanto.foodfinder.R;
import com.nsutanto.foodfinder.fragment.RestaurantLayoutFragment;
import com.nsutanto.foodfinder.util.constant;

import java.util.ArrayList;
import java.util.List;

public class RestaurantPagerAdapter extends FragmentStatePagerAdapter {

    private final List<Fragment> fragmentList = new ArrayList<>();
    private static final int NUM_PAGES = 3;
    private Context context;

    public RestaurantPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;

        RestaurantLayoutFragment lunchFragment = new RestaurantLayoutFragment();
        lunchFragment.setCategory(constant.LUNCH);

        RestaurantLayoutFragment dinnerFragment = new RestaurantLayoutFragment();
        dinnerFragment.setCategory(constant.DINNER);

        RestaurantLayoutFragment favoriteFragment = new RestaurantLayoutFragment();
        favoriteFragment.setCategory(constant.LUNCH);

        fragmentList.add(lunchFragment);
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
            case constant.LUNCH:
                title = context.getString(R.string.lunch);
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