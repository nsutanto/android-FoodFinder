package com.nsutanto.foodfinder.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.nsutanto.foodfinder.R;
import com.nsutanto.foodfinder.apiclient.ZomatoClient;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ZomatoClient.GetRestaurant("Chicago");
    }
}
