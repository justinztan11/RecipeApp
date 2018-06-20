package com.recipe.recipeapp.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;

import com.recipe.recipeapp.Recipe;

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

    public void loadData() {

    }

    public long addWord(Recipe recipe) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(RecipeTable.COL_ID, recipe.getItemID());
        initialValues.put(RecipeTable.COL_NAME, recipe.getName());
        initialValues.put(RecipeTable.COL_DESCRIPTION, recipe.getDescription());
        //initialValues.put(RecipeTable.COL_CATEGORY, recipe.getCategory());
        initialValues.put(RecipeTable.COL_RATING, recipe.getRating());
        //initialValues.put(RecipeTable.COL_REVIEW, recipe.getReviews());
        initialValues.put(RecipeTable.COL_IMAGE, recipe.getImage());

        return mDatabase.insert(RecipeTable.FTS_VIRTUAL_TABLE, null, initialValues);
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
