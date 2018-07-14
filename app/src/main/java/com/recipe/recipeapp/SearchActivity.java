package com.recipe.recipeapp;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.recipe.recipeapp.Adapter.RecipeRecyclerAdapter;
import com.recipe.recipeapp.Database_Recipe.RecipeDataSource;
import com.recipe.recipeapp.Database_Recipe.RecipeTable;
import com.recipe.recipeapp.Objects.Recipe;
import com.recipe.recipeapp.Sample_Data.RecipeData;
import com.recipe.recipeapp.Singleton.CategorySelectedSingleton;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    private RecipeDataSource mDataSource;
    private CoordinatorLayout coordinatorLayout;
    private List<Recipe> recipeList = RecipeData.recipeList;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager; //positions layout of page
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(null);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        spinner = findViewById(R.id.spinner);
        ArrayAdapter<String> listAdapter = new ArrayAdapter<>(SearchActivity.this,
                R.layout.custom_spinner_item,
                getResources().getStringArray(R.array.categories_list));
        listAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(listAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                CategorySelectedSingleton.getInstance().categorySelected =
                        spinner.getSelectedItem().toString();

                Cursor cursor = mDataSource.getRecipeMatches(null, null);
                setSearchDisplay(cursor);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                CategorySelectedSingleton.getInstance().categorySelected = "All";

                Cursor cursor = mDataSource.getRecipeMatches(null, null);
                setSearchDisplay(cursor);
            }
        });

        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinator2);

        // creating and opening database
        mDataSource = new RecipeDataSource(this);
        mDataSource.open();
//        Snackbar.make(coordinatorLayout, "database created", Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show();

        // delete all items in database
        //mDataSource.deleteAll();
        //populate database
        long numItems = mDataSource.getDataItemsCount();
        if (numItems == 0) {
            mDataSource.loadData(recipeList);
        }

        recyclerView = (RecyclerView) findViewById(R.id.recycle_view);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

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
        searchView.setMaxWidth(Integer.MAX_VALUE);

        int searchImgId = searchView.getResources().getIdentifier("android:id/search_mag_icon", null, null);
        ImageView searchImage = (ImageView) searchView.findViewById(searchImgId);
        searchImage.setVisibility(View.GONE);
        searchImage.setImageDrawable(null);

        return true;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);

            Cursor cursor = mDataSource.getRecipeMatches(query, null);
            setSearchDisplay(cursor);
        }
    }

    // given cursor,
    // sets recycler view with appropriate data
    // sets results count display in search page(under toolbar)
    private void setSearchDisplay(Cursor cursor) {

        int matches = (cursor == null) ? 0 : cursor.getCount();
        TextView matchCount = findViewById(R.id.match_count);
        matchCount.setText("Recipe App,  " + matches + " recipe(s) - "
                + spinner.getSelectedItem().toString());

        List<Recipe> resultsList = getResultsList(cursor);
        RecipeRecyclerAdapter adapter = new RecipeRecyclerAdapter(SearchActivity.this, resultsList);
        recyclerView.setAdapter(adapter);
    }

    public List<Recipe> getResultsList(Cursor cursor) {

        List<Recipe> tempList = new ArrayList<>();

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

        return tempList;
    }

}
