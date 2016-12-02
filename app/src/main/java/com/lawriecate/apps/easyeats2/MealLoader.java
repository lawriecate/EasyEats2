package com.lawriecate.apps.easyeats2;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by lawrie on 15/11/2016.
 */

public class MealLoader {
    public ArrayList<Meal> loadMeals(String filename, Context context) {
        final ArrayList<Meal> mealArrayList = new ArrayList<>();

        try {
            // Load data
            String jsonString = readFile(filename, context);
            JSONObject json = new JSONObject(jsonString);
            JSONArray meals = json.getJSONArray("recipes");

            // Get Recipe objects from data
            for(int i = 0; i < meals.length(); i++){
                Log.d("JSON",meals.getJSONObject(i).getString("title"));

                ArrayList<String> instructions = new ArrayList<String>();
                JSONArray jsonInstructions = meals.getJSONObject(i).getJSONArray("instructions");
                for(int j = 0; j < jsonInstructions.length(); j++) {
                    instructions.add(jsonInstructions.getString(j));
                }

                ArrayList<String> ingredients = new ArrayList<String>();
                JSONArray jsonIngredients = meals.getJSONObject(i).getJSONArray("ingredients");
                for(int j = 0; j < jsonIngredients.length(); j++) {
                    ingredients.add(jsonIngredients.getString(j));
                }

                Meal meal = new Meal(
                        meals.getJSONObject(i).getInt("id"),
                        meals.getJSONObject(i).getString("title"),
                        meals.getJSONObject(i).getString("description"),
                        meals.getJSONObject(i).getString("image"),
                        meals.getJSONObject(i).getString("video"),
                        instructions,
                        ingredients
                );

                mealArrayList.add(meal);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return mealArrayList;
    }

    private static String readFile(String filename, Context context) {
        String json = null;

        try {
            InputStream is = context.getAssets().open(filename);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        }
        catch (java.io.IOException ex) {
            ex.printStackTrace();
            return null;
        }

        return json;
    }
}
