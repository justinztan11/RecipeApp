package com.recipe.recipeapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.recipe.recipeapp.Adapter.RecipeRecyclerAdapter;
import com.recipe.recipeapp.Objects.Recipe;
import com.recipe.recipeapp.Sample_Data.RecipeData;

import java.util.List;

public class Tab3AllRecipes extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter; //populates recyclerview based on data
    private RecyclerView.LayoutManager layoutManager; //positions layout of page
    private List<Recipe> recipeList = RecipeData.recipeList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab3_all_recipes, container, false);
        recyclerView = rootView.findViewById(R.id.recycle_view);

        RecipeRecyclerAdapter adapter = new RecipeRecyclerAdapter(recipeList);
//        RecipeRecyclerAdapter adapter = new RecipeRecyclerAdapter(MainActivity.mDataSource.getAll());

        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        return rootView;

    }
}
