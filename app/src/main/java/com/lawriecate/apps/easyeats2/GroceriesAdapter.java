package com.lawriecate.apps.easyeats2;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by lawrie on 15/11/2016.
 */

public class GroceriesAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<Grocery> mDataSource;
    private DatabaseHandler db;

    public GroceriesAdapter(Context context, ArrayList<Grocery> items, DatabaseHandler _db) {
        mContext = context;
        mDataSource = items;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        db = _db;
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
        Grocery grocery = (Grocery) getItem(i);
        return  grocery.getID();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View rowView = mInflater.inflate(R.layout.list_item_grocery, viewGroup, false);

        TextView titleTextView =
                (TextView) rowView.findViewById(R.id.grocery_list_title);
        TextView subtitleTextView =
                (TextView) rowView.findViewById(R.id.grocery_list_subtitle);

        //TextView detailTextView =
        //        (TextView) rowView.findViewById(R.id.meal_list_detail);

        ImageView thumbnailImageView =
                (ImageView) rowView.findViewById(R.id.grocery_list_thumbnail);

        CheckBox isBoughtCheckbox =
                (CheckBox) rowView.findViewById(R.id.grocery_list_checkbox);

        final Grocery grocery = (Grocery) getItem(i);


        titleTextView.setText(grocery.getName());
        Log.i("DBR",grocery.getID() + " " + grocery.getName() + " B" +grocery.getBought());
        isBoughtCheckbox.setChecked(grocery.getBought());


        isBoughtCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                grocery.setBought(isChecked);

                db.updateGrocery(grocery);
                Log.i("DBC",db.getGrocery(grocery.getID()).getName() + " / " + db.getGrocery(grocery.getID()).getBought());
            }
        });

        //subtitleTextView.setText(meal.getDescription());
//        detailTextView.setText(meal.label);

        //Picasso.with(mContext).load(meal.getImage()).placeholder(R.mipmap.ic_launcher).into(thumbnailImageView);
        return rowView;
    }
}
