package com.recipe.recipeapp;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;

import com.recipe.recipeapp.Adapter.RecipeRecyclerAdapter;
import com.recipe.recipeapp.Database.DataSource;
import com.recipe.recipeapp.Database.RecipeTable;
import com.recipe.recipeapp.Objects.Recipe;
import com.recipe.recipeapp.SampleData.RecipeData;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private DataSource mDataSource;
    private CoordinatorLayout coordinatorLayout;
    private List<Recipe> recipeList = RecipeData.recipeList;
    private RecyclerView recyclerView;
    //private RecyclerView.Adapter adapter; //populates recyclerview based on data
    private RecyclerView.LayoutManager layoutManager; //positions layout of page

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinator);

        // creating and opening database
        mDataSource = new DataSource(this);
        mDataSource.open();
        Snackbar.make(coordinatorLayout, "database created", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();

        // delete all items in database
        //mDataSource.deleteAll();
        //populate database
        mDataSource.loadData(recipeList);

        recyclerView = (RecyclerView) findViewById(R.id.recycleView);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // handle ACTION_SEARCH intent
        handleIntent(getIntent());

    }

    @Override
    protected void onPause() {
        super.onPause();
        mDataSource.close();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mDataSource.open();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_options, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id) {
            case R.id.action_settings:
                Snackbar.make(coordinatorLayout,
                        "You selected settings", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                return true;
            case R.id.action_logout:
                Snackbar.make(coordinatorLayout,
                        "You selected logout", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onNewIntent(Intent intent) {
       // setIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            Cursor cursor = mDataSource.getWordMatches(query, null);

            RecipeRecyclerAdapter adapter = new RecipeRecyclerAdapter(getResultsList(cursor));
            recyclerView.setAdapter(adapter);
        }
    }

    public List<Recipe> getResultsList(Cursor cursor) {
        List<Recipe> tempList = new ArrayList<>();

        Log.d("handleIntent", "got intent");
        Log.d("count", "cursor count: " + cursor.getCount());

        if (cursor != null) {
            try {
                // cursor starts at index 0, needs to execute below block only once before moving
                do {
                    Recipe recipe = new Recipe();
                    recipe.setRecipeID(cursor.getString(cursor.getColumnIndex(RecipeTable.COL_ID)));
                    recipe.setName(cursor.getString(cursor.getColumnIndex(RecipeTable.COL_NAME)));
                    recipe.setDescription(cursor.getString(cursor.getColumnIndex(RecipeTable.COL_DESCRIPTION)));
                    recipe.setImage(cursor.getString(cursor.getColumnIndex(RecipeTable.COL_IMAGE)));
                    recipe.setRating(cursor.getFloat(cursor.getColumnIndex(RecipeTable.COL_RATING)));
                    tempList.add(recipe);
                    Log.d("searchOutput", "Search Output: " + recipe.getName());

                } while (cursor.moveToNext());

            } finally {
                cursor.close();
            }
        }

        // print output from cursor
        String result = "";
        for (Recipe recipe : tempList) {
            result += recipe.getName() + " ";
        }
        Log.d("listOutput", "Cursor List: " + result);

        return tempList;
    }

    public void toAllRecipe (View view){
            Intent intent = new Intent(this, AllDataActivity.class);
            startActivity(intent);
        }

    }
