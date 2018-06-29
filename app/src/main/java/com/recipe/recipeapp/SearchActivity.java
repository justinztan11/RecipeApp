package com.recipe.recipeapp;

import android.app.SearchManager;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.recipe.recipeapp.Adapter.RecipeRecyclerAdapter;
import com.recipe.recipeapp.Objects.Recipe;
import com.recipe.recipeapp.SampleData.RecipeData;

import java.util.List;

public class SearchActivity extends AppCompatActivity {


    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter; //populates recyclerview based on data
    private RecyclerView.LayoutManager layoutManager; //positions layout of page
    private List<Recipe> recipeList = RecipeData.recipeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView =  (RecyclerView)findViewById(R.id.recycleView);

        Recipe recipe1 = new Recipe("a", "mac and cheese", "cheesy and maccy", null, 2.5f, null, null, null);
        Recipe recipe2 = new Recipe("b", "hot dog", "a sausage in a bun", null, 1.3f, null, null, null);
        Recipe recipe3 = new Recipe("c", "pizza", "why arent you a fatty", null, 2.1f, null, null, null);

        Recipe[] data = {recipe1, recipe2, recipe3};
        RecipeRecyclerAdapter adapter = new RecipeRecyclerAdapter(data);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        handleIntent(getIntent());
        //RecipeListAdapter adapter = new RecipeListAdapter(
        //        this, R.layout.recipe_item, recipeList);
        //ListView lv = findViewById(R.id.listView);
        //lv.setAdapter(adapter);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            Cursor c = MainActivity.mDataSource.getWordMatches(query, null);
            //use the query to search your data somehow
        }
    }

}
