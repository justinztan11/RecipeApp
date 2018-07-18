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
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;

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
    private Spinner categorySpinner;
    private Spinner sortSpinner;
    RecipeRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinator2);


        // TOOLBAR
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(null);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        // DATABASE
        // creating and opening database
        mDataSource = new RecipeDataSource(this);
        mDataSource.open();
        // delete all items in database
        mDataSource.deleteAll();
        //populate database if not already existent
        long numItems = mDataSource.getDataItemsCount();
        if (numItems == 0) {
            mDataSource.loadData(recipeList);
        }


        // RECYCLER VIEW
        // initialized recycler view
        recyclerView = (RecyclerView) findViewById(R.id.recycle_view);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


        // ADAPTER
        // from list to recycler view
        adapter = new RecipeRecyclerAdapter(SearchActivity.this, new ArrayList<Recipe>());


        // SPINNER - CATEGORY
        categorySpinner = findViewById(R.id.spinner_category);
        ArrayAdapter<String> categoryListAdapter = new ArrayAdapter<>(SearchActivity.this,
                R.layout.custom_spinner_item,
                getResources().getStringArray(R.array.categories_list));
        categoryListAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(categoryListAdapter);

        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                CategorySelectedSingleton.getInstance().categorySelected =
                        categorySpinner.getSelectedItem().toString();

                Cursor cursor = mDataSource.getRecipeMatches(null, null);
                setSearchDisplay(cursor);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                CategorySelectedSingleton.getInstance().categorySelected = "All";

            }
        });


        // SPINNER - SORTING
        sortSpinner = findViewById(R.id.spinner_sort);
        ArrayAdapter<String> sortListAdapter = new ArrayAdapter<>(SearchActivity.this,
                R.layout.custom_spinner_item,
                getResources().getStringArray(R.array.sorting_list));
        sortListAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sortSpinner.setAdapter(sortListAdapter);

        sortSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                setSortView(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        // set default search display to all recipes
        Cursor cursor = mDataSource.getRecipeMatches(null, null);
        setSearchDisplay(cursor);

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
                + categorySpinner.getSelectedItem().toString());

        List<Recipe> resultsList = getResultsList(cursor);
        adapter.setRecipeList(resultsList);
        recyclerView.setAdapter(adapter);

        int sortItem = sortSpinner.getSelectedItemPosition();
        setSortView(sortItem);
    }

    private void setSortView (int position) {

        switch (position) {
            case 0: // sort by alphabet
                adapter.sortRecipeAlpha();
                break;
            case 1: // sort by rating
                adapter.sortRecipeRating();
                break;
        }

        adapter.notifyDataSetChanged();
    }

    private List<Recipe> getResultsList(Cursor cursor) {

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
