package com.recipe.recipeapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.recipe.recipeapp.Database.IngredientDataSource;
import com.recipe.recipeapp.Database.RecipeDataSource;
import com.recipe.recipeapp.Objects.Ingredient;
import com.recipe.recipeapp.Objects.Recipe;
import com.recipe.recipeapp.Sample_Data.IngredientData;
import com.recipe.recipeapp.Sample_Data.RecipeData;

import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    // Where all database functions will be executed
    public static RecipeDataSource mRecipeDataSource;
    public static IngredientDataSource mIngredientDataSource;

    // Where sample data is stored
    private List<Recipe> recipeList;
    private List<Ingredient> ingredientList;

    private CoordinatorLayout coordinatorLayout;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;

    public FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinator);


        // sample data
        recipeList = RecipeData.recipeList;
        ingredientList = IngredientData.ingredientList;


        // DATABASE - RECIPE
        // creating and opening database
        mRecipeDataSource = new RecipeDataSource(this);
        mRecipeDataSource.open();
        // delete all items in database
        mRecipeDataSource.deleteAll();


        // DATABASE - INGREDIENT
        // creating and opening database
        mIngredientDataSource = new IngredientDataSource(this);
        mIngredientDataSource.open();
        // delete all items in database
        mIngredientDataSource.deleteAll();


        // POPULATE DATABASES
        // populate Ingredient database if not already existent
        long numIngredientItems = mIngredientDataSource.getDataItemsCount();
        if (numIngredientItems == 0) {
            mIngredientDataSource.loadData(ingredientList);
        }

        // populate Recipe database if not already existent
        long numItems = mRecipeDataSource.getDataItemsCount();
        if (numItems == 0) {
            mRecipeDataSource.loadData(recipeList);
        }


        // NAVIGATION DRAWER
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        // TABS
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());


        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

        //FLOATING ACTION BUTTON
        fab = findViewById(R.id.fab);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            Intent intent = new Intent(this, SearchActivity.class);
            this.startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);

    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.my_recipe) {

        } else if (id == R.id.setting) {

        } else if (id == R.id.nav_share) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    Tab1Featured tab1 = new Tab1Featured();
                    return tab1;
                case 1:
                    Tab2IngredientSearch tab2 = new Tab2IngredientSearch();
                    return tab2;
                case 2:
                    Tab3AddRecipe tab3 = new Tab3AddRecipe();
                    return tab3;
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

    }


//    public void toSomeRecipe(View view) {
//        Intent intent = new Intent(this, SomeActivity.class);
//        startActivity(intent);
//    }


}
