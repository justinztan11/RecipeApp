package com.recipe.recipeapp.Database_Recipe;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;
import android.widget.Spinner;

import com.recipe.recipeapp.MainActivity;
import com.recipe.recipeapp.Objects.Recipe;
import com.recipe.recipeapp.SearchActivity;
import com.recipe.recipeapp.Singleton.CategorySelectedSingleton;

import java.util.ArrayList;
import java.util.List;

public class RecipeDataSource {

    private Context mContext;
    private SQLiteDatabase mDatabase;
    private RecipeDatabaseOpenHelper mDbHelper;

    public RecipeDataSource(Context context) {
        this.mContext = context;
        mDbHelper = new RecipeDatabaseOpenHelper(mContext);
        mDatabase = mDbHelper.getWritableDatabase();
    }

    public void open() {
        mDbHelper.getWritableDatabase();
    }

    public void close() {
        mDbHelper.close();
    }

    public void loadData(List<Recipe> recipeList) {
        for (Recipe recipe: recipeList) {
            try {
                addRecipe(recipe);
                Log.d("OUTPUT", "loadData: " + recipe.getRecipeID() );
                Log.d("OUTPUT", "loadData: " + recipe.getName() );
                Log.d("OUTPUT", "loadData: " + recipe.getDescription());
                Log.d("OUTPUT", "loadData: " + recipe.getImage() );
                Log.d("OUTPUT", "loadData: " + recipe.getRating() );
                Log.d("OUTPUT", "loadData: " + recipe.getCategory());
            } catch (SQLiteException e) {
                e.printStackTrace();
            }
        }
    }

    public void addRecipe(Recipe recipe) {
        ContentValues initialValues = new ContentValues(5);

        initialValues.put(RecipeTable.COL_ID, recipe.getRecipeID());
        initialValues.put(RecipeTable.COL_NAME, recipe.getName());
        initialValues.put(RecipeTable.COL_DESCRIPTION, recipe.getDescription());
        initialValues.put(RecipeTable.COL_IMAGE, recipe.getImage());
        initialValues.put(RecipeTable.COL_RATING, recipe.getRating());
        initialValues.put(RecipeTable.COL_CATEGORY, recipe.getCategory());
        //initialValues.put(RecipeTable.COL_REVIEW, recipe.getReviews());

        mDatabase.insert(RecipeTable.FTS_VIRTUAL_TABLE, null, initialValues);
    }

    public void deleteAll() {
        mDatabase.delete(RecipeTable.FTS_VIRTUAL_TABLE, null, null);
    }

    public List<Recipe> getAll() {
        List<Recipe> recipeList = new ArrayList<>();
        Cursor cursor = mDatabase.query(RecipeTable.FTS_VIRTUAL_TABLE, RecipeTable.ALL_COL,
                null, null, null, null, null);

        while(cursor.moveToNext()) {
            Recipe recipe = new Recipe();
            recipe.setRecipeID(cursor.getString(cursor.getColumnIndex(RecipeTable.COL_ID)));
            recipe.setName(cursor.getString(cursor.getColumnIndex(RecipeTable.COL_NAME)));
            recipe.setDescription(cursor.getString(cursor.getColumnIndex(RecipeTable.COL_DESCRIPTION)));
            recipe.setImage(cursor.getString(cursor.getColumnIndex(RecipeTable.COL_IMAGE)));
            recipe.setRating(cursor.getFloat(cursor.getColumnIndex(RecipeTable.COL_RATING)));
            recipe.setCategory(cursor.getString(cursor.getColumnIndex(RecipeTable.COL_CATEGORY)));
            recipeList.add(recipe);
        }

        cursor.close();

        return recipeList;
    }

    public Cursor getRecipeMatches(String query, String[] columns) {

        // the category selected from the search page
        String categorySelected = CategorySelectedSingleton.getInstance()
                .categorySelected.toLowerCase();
        Log.d("Category Selected", "getRecipeMatches - Category: " + categorySelected);

        String selection = RecipeTable.FTS_VIRTUAL_TABLE + " MATCH ?";
        String[] selectionArgs;
        if (query == null) {
            if (categorySelected.equals("all")) {
                selection = null;
                selectionArgs = null;
                columns = RecipeTable.ALL_COL;
            } else {
                selectionArgs = new String[]{RecipeTable.COL_CATEGORY + ":" + categorySelected};
            }
        } else {
            if (categorySelected.equals("all")) {
                selectionArgs = new String[]{RecipeTable.COL_NAME + ":" + query + "* OR "
                        + RecipeTable.COL_DESCRIPTION + ":" + query + "*"};
            } else {
                selection = RecipeTable.COL_CATEGORY + " MATCH ?";
                selectionArgs = new String[]{RecipeTable.COL_NAME + ":" + query + "* OR "
                        + RecipeTable.COL_DESCRIPTION + ":" + query + "*" + categorySelected};
            }
        }


        return query(selection, selectionArgs, columns);
    }

    private Cursor query(String selection, String[] selectionArgs, String[] columns) {
        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
        builder.setTables(RecipeTable.FTS_VIRTUAL_TABLE);

        Cursor cursor = builder.query(mDbHelper.getReadableDatabase(),
                columns, selection, selectionArgs, null, null, null);
        
        if (cursor == null) {
            return null;
        } else if (!cursor.moveToFirst()) {
            cursor.close();
            return null;
        }
        return cursor;
    }

    public long getDataItemsCount() {
        return DatabaseUtils.queryNumEntries(mDatabase, RecipeTable.FTS_VIRTUAL_TABLE);
    }


}


