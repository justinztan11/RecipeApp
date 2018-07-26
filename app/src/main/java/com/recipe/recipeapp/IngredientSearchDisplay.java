package com.recipe.recipeapp;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.recipe.recipeapp.Adapter.IngredientRecyclerAdapter;
import com.recipe.recipeapp.Adapter.RecipeRecyclerAdapter;
import com.recipe.recipeapp.Database_Recipe.RecipeDataSource;
import com.recipe.recipeapp.Objects.Recipe;
import com.recipe.recipeapp.Sample_Data.RecipeData;
import com.recipe.recipeapp.Singleton.IngredientsSelectedSingleton;

import java.util.ArrayList;
import java.util.List;

public class IngredientSearchDisplay extends AppCompatActivity {

    private RecipeDataSource mRecipeDataSource;
    private RecyclerView recyclerView;
    private RecipeRecyclerAdapter adapter;

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

        // ADAPTER
        // from list to recycler view
        List<Recipe> temp = RecipeData.recipeList;
        adapter = new RecipeRecyclerAdapter(this, temp);
        recyclerView.setAdapter(adapter);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //do whatever you want here
        finish();
        return true;
    }
}
