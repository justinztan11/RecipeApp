package com.recipe.recipeapp;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.recipe.recipeapp.Adapter.RecipeRecyclerAdapter;
import com.recipe.recipeapp.Database.RecipeDataSource;
import com.recipe.recipeapp.Objects.Recipe;
import com.recipe.recipeapp.Sample_Data.RecipeData;
import com.recipe.recipeapp.Singleton.IngredientsSelectedSingleton;

import java.util.List;

public class IngredientSearchDisplay extends AppCompatActivity {

    private RecipeDataSource mRecipeDataSource;
    private RecyclerView recyclerView;
    private RecipeRecyclerAdapter adapter;
    private Spinner sortSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredient_search_display);

        // TOOLBAR
        Toolbar toolbar = (Toolbar) findViewById(R.id.ingredient_display_toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        // DATABASE
        // getting a reference to the DB created in Main Activity
        mRecipeDataSource = MainActivity.mRecipeDataSource;


        // RECYCLER VIEW
        // initialized recycler view
        recyclerView = (RecyclerView) findViewById(R.id.ingredient_display_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // getting ingredients user selected for Ingredient Search
        List<String> ingredientList = IngredientsSelectedSingleton.getInstance().ingredientList;

        Cursor cursor = mRecipeDataSource.getRecipeFromIngredients(ingredientList);

        // ADAPTER
        // from list to recycler view
//        List<Recipe> temp = RecipeData.recipeList;

        List<Recipe> temp = mRecipeDataSource.getResultsList(cursor);
        adapter = new RecipeRecyclerAdapter(this, temp);
        recyclerView.setAdapter(adapter);

        // SPINNER - SORTING
        sortSpinner = findViewById(R.id.spinner_sort);
        ArrayAdapter<String> sortListAdapter = new ArrayAdapter<>(this,
                R.layout.custom_spinner_item,
                getResources().getStringArray(R.array.sorting_list));
        sortListAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sortSpinner.setAdapter(sortListAdapter);

        sortSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                switch (position) {
                    case 0: // sort by alphabet
                        adapter.sortRecipeAlpha();
                        break;
                    case 1: // sort by rating
                        adapter.sortRecipeRating();
                        break;
                }
                adapter.notifyDataSetChanged();


                int matches = adapter.getItemCount();
                TextView matchCount = findViewById(R.id.match_count);
                matchCount.setText("Recipe App,  " + matches + " recipe(s)");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //do whatever you want here
        finish();
        return true;
    }
}
