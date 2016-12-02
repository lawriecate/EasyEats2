package com.lawriecate.apps.easyeats2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by lawrie on 15/11/2016.
 */

public class MealAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<Meal> mDataSource;

    public MealAdapter(Context context, ArrayList<Meal> items) {
        mContext = context;
        mDataSource = items;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return mDataSource.size();
    }

    @Override
    public Object getItem(int i) {
        return mDataSource.get(i);
    }

    @Override
    public long getItemId(int i) {
        Meal meal = (Meal) getItem(i);
        return  meal.getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View rowView = mInflater.inflate(R.layout.list_item_meal, viewGroup, false);

        TextView titleTextView =
                (TextView) rowView.findViewById(R.id.meal_list_title);
        TextView subtitleTextView =
                (TextView) rowView.findViewById(R.id.meal_list_subtitle);

        TextView detailTextView =
                (TextView) rowView.findViewById(R.id.meal_list_detail);

        ImageView thumbnailImageView =
                (ImageView) rowView.findViewById(R.id.meal_list_thumbnail);

        Meal meal = (Meal) getItem(i);


        titleTextView.setText(meal.getTitle());
        subtitleTextView.setText(meal.getDescription());
//        detailTextView.setText(meal.label);

        Picasso.with(mContext).load(meal.getImage()).placeholder(R.mipmap.ic_launcher).into(thumbnailImageView);
        return rowView;
    }
}
