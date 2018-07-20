package com.recipe.recipeapp;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.recipe.recipeapp.Database_Ingredient.IngredientTable;
import com.recipe.recipeapp.Objects.Ingredient;

import java.util.ArrayList;
import java.util.List;

public class Tab2IngredientSearch extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab2_ingredient_search, container, false);

        String[] ingredientNames = getIngredientNamesFromDB();
        final AutoCompleteTextView autoComplete = rootView.findViewById(R.id.ingredient_auto_complete);
        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_dropdown_item_1line, ingredientNames);
        autoComplete.setAdapter(stringArrayAdapter);

        autoComplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(), "entered ingredient " + autoComplete.getText(), Toast.LENGTH_LONG).show();
            }
        });

        return rootView;
    }

    public String[] getIngredientNamesFromDB(){

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
