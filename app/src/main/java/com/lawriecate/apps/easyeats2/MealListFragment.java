package com.lawriecate.apps.easyeats2;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class MealListFragment extends Fragment {
    private ListView mListView;

    private MealAdapter adapter;

    private MenuChangeListener menuChangeListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            menuChangeListener = (MenuChangeListener) context;
        } catch (ClassCastException castException) {
            /** The activity does not implement the listener. */
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.activity_main, container, false);



        mListView = (ListView) rootView.findViewById(R.id.meal_list_view);

        loadRecipies("breakfast.json");


        Button btnBreakfast = (Button) rootView.findViewById(R.id.btnBreakfast);
        btnBreakfast.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                loadRecipies("breakfast.json");
            }
        });
        Button btnLunch = (Button) rootView.findViewById(R.id.btnLunch);
        btnLunch.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                loadRecipies("lunch.json");
            }
        });
        Button btnDinner = (Button) rootView.findViewById(R.id.btnDinner);
        btnDinner.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                loadRecipies("dinner.json");
            }
        });

        return rootView;

    }

    private void loadRecipies(String jsonFile)
    {
        MealLoader mealLoader = new MealLoader();
        final ArrayList<Meal> mealList = mealLoader.loadMeals(jsonFile, this.getContext());

        MealAdapter adapter = new MealAdapter(this.getContext(), mealList);
        mListView.setAdapter(adapter);
        final Context context = this.getContext();
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Meal selectedMeal = mealList.get(position);

                Intent detailIntent = new Intent(context, MealDetailActivity.class);
                detailIntent.putExtra("meal", selectedMeal);

                //detailIntent.putExtra("menuChangeListener", menuChangeListener);
                startActivity(detailIntent);
            }

        });
    }
}
