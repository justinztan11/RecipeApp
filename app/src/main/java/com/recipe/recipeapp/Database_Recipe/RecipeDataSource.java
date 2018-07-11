package com.recipe.recipeapp.Database_Recipe;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;

import com.recipe.recipeapp.Objects.Recipe;

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
        //initialValues.put(RecipeTable.COL_CATEGORY, recipe.getCategory());
        initialValues.put(RecipeTable.COL_IMAGE, recipe.getImage());
        initialValues.put(RecipeTable.COL_RATING, recipe.getRating());
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
            recipeList.add(recipe);
        }

        cursor.close();

        return recipeList;
    }

    public Cursor getRecipeMatches(String query, String[] columns) {
        String selection = RecipeTable.FTS_VIRTUAL_TABLE + " MATCH ?";
        String[] selectionArgs = new String[]{RecipeTable.COL_NAME + ":" + query + "* OR "
                + RecipeTable.COL_DESCRIPTION + ":" + query + "*"};

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


