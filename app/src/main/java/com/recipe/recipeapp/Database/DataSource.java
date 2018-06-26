package com.recipe.recipeapp.Database;

import android.app.SearchManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;

import com.recipe.recipeapp.Recipe;

import java.util.List;

public class DataSource {

    private Context mContext;
    SQLiteDatabase mDatabase;
    DatabaseOpenHelper mDbHelper;

    public DataSource(Context context) {
        this.mContext = context;
        mDbHelper = new DatabaseOpenHelper(mContext);
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
                addWord(recipe);

                Log.d("OUTPUT", "loadData: " + recipe.getRecipeID() );
                Log.d("OUTPUT", "loadData: " + recipe.getName() );
                Log.d("OUTPUT", "loadData: " + recipe.getDescription());
                Log.d("OUTPUT", "loadData: " + recipe.getImage() );
                Log.d("OUTPUT", "loadData: " + recipe.getRating() );
            } catch (SQLiteException e) {
                e.printStackTrace();
            }
        }
    }

    public void addWord(Recipe recipe) {
        ContentValues initialValues = new ContentValues(5);

        initialValues.put(RecipeTable.COL_ID, recipe.getRecipeID());
        initialValues.put(RecipeTable.COL_NAME, recipe.getName());
        initialValues.put(RecipeTable.COL_DESCRIPTION, recipe.getDescription());
        //initialValues.put(RecipeTable.COL_CATEGORY, recipe.getCategory());
        initialValues.put(RecipeTable.COL_IMAGE, recipe.getImage());
        initialValues.put(RecipeTable.COL_RATING, recipe.getRating());
        //initialValues.put(RecipeTable.COL_REVIEW, recipe.getReviews());

        mDatabase.insert(RecipeTable.FTS_VIRTUAL_TABLE, null, initialValues);
    }

    public void deleteAll() {
        mDatabase.delete(RecipeTable.FTS_VIRTUAL_TABLE, null, null);
    }

    public Cursor getWordMatches(String query, String[] columns) {
        String selection = RecipeTable.COL_NAME + " MATCH ?";
        String[] selectionArgs = new String[]{query + "*"};

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


}


