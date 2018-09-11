package com.nsutanto.foodfinder.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nsutanto.foodfinder.R;
import com.nsutanto.foodfinder.listener.IRestaurantAdapterListener;
import com.nsutanto.foodfinder.listener.IRestaurantInfoAdapterListener;
import com.nsutanto.foodfinder.model.Restaurant;
import com.nsutanto.foodfinder.model.RestaurantInfo;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RestaurantInfoAdapter extends RecyclerView.Adapter<RestaurantInfoAdapter.ViewHolder> {

    private List<RestaurantInfo> restaurantInfos = new ArrayList<>();
    private Context context;
    private static IRestaurantInfoAdapterListener restaurantListener;


    public RestaurantInfoAdapter(IRestaurantInfoAdapterListener restaurantListener) {
        this.restaurantListener = restaurantListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.iv_restaurant)
        ImageView iv_restaurant;

        @BindView(R.id.tv_restaurant_name)
        TextView tv_restaurant;

        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
            v.setOnClickListener(this);
        }

        public void bind(RestaurantInfo restaurantInfo) {
            tv_restaurant.setText(restaurantInfo.getName());
            String posterPath = restaurantInfo.getThumb();
            if (posterPath.equals("") == false) {
                Picasso.get()
                        .load(posterPath)
                        .into(iv_restaurant);
            }
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            restaurantListener.onRestaurantClick(restaurantInfos.get(position));
        }
    }

    @Override
    public RestaurantInfoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.restaurant_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RestaurantInfo restaurantInfo = restaurantInfos.get(position);
        holder.bind(restaurantInfo);
    }

    @Override
    public int getItemCount() {
        if (restaurantInfos != null) {
            return restaurantInfos.size();
        } else {
            return 0;
        }
    }

    public void setRestaurants(List<RestaurantInfo> restaurantInfos) {
        if (restaurantInfos != null) {

            for (Iterator<RestaurantInfo> iter = restaurantInfos.listIterator(); iter.hasNext(); ) {
                RestaurantInfo restaurantInfo = iter.next();
                if (restaurantInfo.getThumb().equals("")) {
                    iter.remove();
                }
            }

            this.restaurantInfos = restaurantInfos;
            notifyDataSetChanged();
        }
    }
}