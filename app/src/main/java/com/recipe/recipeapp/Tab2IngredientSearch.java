package com.recipe.recipeapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.sip.SipSession;
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
import android.widget.ImageView;
import android.widget.Toast;

import com.recipe.recipeapp.Adapter.IngredientRecyclerAdapter;
import com.recipe.recipeapp.Adapter.RecipeRecyclerAdapter;
import com.recipe.recipeapp.Database_Ingredient.IngredientTable;
import com.recipe.recipeapp.Objects.Ingredient;
import com.recipe.recipeapp.Objects.Recipe;
import com.recipe.recipeapp.Singleton.IngredientsSelectedSingleton;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;

public class Tab2IngredientSearch extends Fragment {

    private RecyclerView recyclerView;
    public IngredientRecyclerAdapter adapter;
    private String[] ingredientNames;
    private AutoCompleteTextView autoComplete;
    AlertDialog alert;
    private ImageView refresh;

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
        List<String> ingredientList = IngredientsSelectedSingleton.getInstance().ingredientList;
        adapter = new IngredientRecyclerAdapter(getContext(), ingredientList);
        recyclerView.setAdapter(adapter); // set adapter


        // Array of ingredient names from Ingredient DB
        ingredientNames = getIngredientNamesFromDB();


        // AUTO-COMPLETE TEXT
        autoComplete = rootView.findViewById(R.id.ingredient_auto_complete);;
        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(getContext(),
                R.layout.custom_dropdown, ingredientNames);
        autoComplete.setAdapter(stringArrayAdapter);

        // when item in drop-down menu is selected
        autoComplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String ingredient = autoComplete.getText().toString();

                boolean added = adapter.addIngredient(ingredient);
                adapter.notifyDataSetChanged();

                // allow ingredient list data to be accessible in another activity other than parent
                IngredientsSelectedSingleton.getInstance().ingredientList = adapter.getIngredientList();

                if (added == false) {
                    Toast.makeText(getActivity(), "Already Added "
                            + autoComplete.getText().toString(), Toast.LENGTH_LONG).show();
                }
            }
        });


        // ALERT DIALOG
        // prompts the user to confirm removing all ingredients
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("Remove All?");
        builder.setCancelable(true);

        builder.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        adapter.removeAllIngredients();
                        adapter.notifyDataSetChanged();
                    }
                });
        builder.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        alert = builder.create();


        // REFRESH BUTTON
        // removes all selected ingredients
        refresh = rootView.findViewById(R.id.refresh);
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (adapter.getItemCount() > 0) {
                    alert.show();
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
        MainActivity mainActivity = (MainActivity) getActivity();
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
