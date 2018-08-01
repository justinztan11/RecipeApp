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
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;

import com.recipe.recipeapp.Adapter.RecipeRecyclerAdapter;
import com.recipe.recipeapp.Database.RecipeDataSource;
import com.recipe.recipeapp.Objects.Recipe;
import com.recipe.recipeapp.Singleton.CategorySelectedSingleton;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    private RecipeDataSource mRecipeDataSource;
    private CoordinatorLayout coordinatorLayout;
    private RecyclerView recyclerView;
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
        // getting a reference to the DB created in Main Activity
        mRecipeDataSource = MainActivity.mRecipeDataSource;


        // RECYCLER VIEW
        // initialized recycler view
        recyclerView = (RecyclerView) findViewById(R.id.recycle_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


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

                Cursor cursor = mRecipeDataSource.getRecipeMatches(null, null);
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
        Cursor cursor = mRecipeDataSource.getRecipeMatches(null, null);
        setSearchDisplay(cursor);

        // handle ACTION_SEARCH intent
        handleIntent(getIntent());
    }

    @Override
    protected void onPause() {
        super.onPause();
        mRecipeDataSource.close();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mRecipeDataSource.open();
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

            Cursor cursor = mRecipeDataSource.getRecipeMatches(query, null);
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

        List<Recipe> resultsList = mRecipeDataSource.getResultsList(cursor);
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



}
