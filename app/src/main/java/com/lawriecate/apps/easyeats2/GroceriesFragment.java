package com.lawriecate.apps.easyeats2;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.CompoundButton;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link GroceriesFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link GroceriesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GroceriesFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

   // private OnFragmentInteractionListener mListener;

    public GroceriesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GroceriesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GroceriesFragment newInstance(String param1, String param2) {
        GroceriesFragment fragment = new GroceriesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    private void loadThemGroceries(View rootView) {
        // Inflate the layout for this fragment

        Log.d("Reading: ", "Reading all groceries");

        final ListView groceriesListView = (ListView) rootView.findViewById(R.id.groceries_list_view);
        //String groceriesList = "";
        //for (Grocery grocery : groceries) {
        //  groceriesList += grocery.getName() + "\n";
        //groceriesListView.
        //}
        //TextView groceriesListText = (TextView) rootView.findViewById(R.id.text_groceries);
        //groceriesListText.setText(groceriesList);
        final DatabaseHandler db = new DatabaseHandler(this.getContext());
        final List<Grocery> groceries = db.getAllGroceries();
        ArrayList<Grocery> adaptedGroceries = new ArrayList<Grocery>();
        for (Grocery grocery : groceries) {
            adaptedGroceries.add(grocery);
        }


        GroceriesAdapter adapter = new GroceriesAdapter(this.getContext(), adaptedGroceries, db);
        groceriesListView.setAdapter(adapter);
        final Context context = this.getContext();

        groceriesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

             /*   Meal selectedMeal = mealList.get(position);

                Intent detailIntent = new Intent(context, MealDetailActivity.class);
                detailIntent.putExtra("meal", selectedMeal);

                //detailIntent.putExtra("menuChangeListener", menuChangeListener);
                startActivity(detailIntent);
                */
                //groceries.get(((int) id)).setBought(!groceries.get((int) id).getBought());
            }

        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_groceries, container, false);
        final DatabaseHandler db = new DatabaseHandler(this.getContext());
        loadThemGroceries(rootView);
        Button clearAllButton = (Button) rootView.findViewById(R.id.button_clear_all);
        clearAllButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.deleteAllGrocery();
                loadThemGroceries(rootView);
            }
        });

        return rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        /*if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }*/
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
       /* if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }\*/
    }

    @Override
    public void onDetach() {
        super.onDetach();
       // mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }*/
}
