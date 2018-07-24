package com.recipe.recipeapp;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.recipe.recipeapp.Adapter.IngredientRecyclerAdapter;
import com.recipe.recipeapp.Adapter.RecipeRecyclerAdapter;
import com.recipe.recipeapp.Database_Ingredient.IngredientTable;
import com.recipe.recipeapp.Objects.Ingredient;
import com.recipe.recipeapp.Objects.Recipe;

import java.util.ArrayList;
import java.util.List;

public class Tab2IngredientSearch extends Fragment {

    RecyclerView recyclerView;
    IngredientRecyclerAdapter adapter;
    String[] ingredientNames;
    AutoCompleteTextView autoComplete;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab2_ingredient_search, container, false);

        // RECYCLER VIEW
        // initialized recycler view
        recyclerView = rootView.findViewById(R.id.ingredient_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // ADAPTER
        // from list to recycler view
        List<String> tempList = new ArrayList<>();
//        tempList.add("Corn");
//        tempList.add("Broccoli");
        adapter = new IngredientRecyclerAdapter(getContext(), tempList);
        recyclerView.setAdapter(adapter); // set adapter

        // Array of ingredient names from Ingredient DB
        ingredientNames = getIngredientNamesFromDB();

        // AUTO-COMPLETE TEXT
        autoComplete = rootView.findViewById(R.id.ingredient_auto_complete);
        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_dropdown_item_1line, ingredientNames);
        autoComplete.setAdapter(stringArrayAdapter);

        // when item in drop-down menu is selected
        autoComplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String ingredient = autoComplete.getText().toString();

                boolean added = adapter.addIngredient(ingredient);
                adapter.notifyDataSetChanged();

                if (added == false) {
                    Toast.makeText(getActivity(), "Already Added "
                            + autoComplete.getText().toString(), Toast.LENGTH_LONG).show();
                }
            }
        });

        return rootView;
    }

    @Override
    public void setUserVisibleHint(boolean visible) {
        super.setUserVisibleHint(visible);
        if (visible && isResumed()) {
            onResume();
        }
    }


    @Override
    public void onResume() {
        super.onResume();

        if (!getUserVisibleHint()) {
            return;
        }

        // FLOATING ACTION BUTTON
        // set onclick listener
        MainActivity mainActivity = (MainActivity)getActivity();
        FloatingActionButton fabSearch = mainActivity.fab;

        fabSearch.setImageResource(R.drawable.ic_search);
        fabSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), IngredientSearchDisplay.class);
                startActivity(intent);
            }
        });
    }

        public String[] getIngredientNamesFromDB() {

        // add items on the array dynamically
        Cursor matches = MainActivity.mIngredientDataSource.getIngredientMatches(null, null);
        List<Ingredient> ingredientList = getResultsList(matches);

        int size = ingredientList.size();
        String[] ingredientNames = new String[size];

        for (int i = 0; i < size; i++) {
            ingredientNames[i] = ingredientList.get(i).getName();
        }

        return ingredientNames;
    }

    private List<Ingredient> getResultsList(Cursor cursor) {

        List<Ingredient> tempList = new ArrayList<>();

        if (cursor != null) {
            try {
                // cursor starts at index 0, needs to execute below block only once before moving
                do {
                    Ingredient ingredient = new Ingredient();
                    ingredient.setIngredientID(cursor.getString(cursor.getColumnIndex(IngredientTable.COL_ID)));
                    ingredient.setName(cursor.getString(cursor.getColumnIndex(IngredientTable.COL_NAME)));
                    tempList.add(ingredient);
                    Log.d("searchOutput", "Search Output: " + ingredient.getName());

                } while (cursor.moveToNext());

            } finally {
                cursor.close();
            }
        }

        return tempList;
    }

}
