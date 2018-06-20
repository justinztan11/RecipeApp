package com.recipe.recipeapp;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

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

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView =  (RecyclerView)findViewById(R.id.recycleView);

        String[] data = {"Card 1 ... desciption", "Card 2", "Card 3"};
        RecipeRecyclerAdapter adapter = new RecipeRecyclerAdapter(data);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(adapter);

        //RecipeListAdapter adapter = new RecipeListAdapter(
        //        this, R.layout.recipe_item, recipeList);
        //ListView lv = findViewById(R.id.listView);
        //lv.setAdapter(adapter);
    }

}
