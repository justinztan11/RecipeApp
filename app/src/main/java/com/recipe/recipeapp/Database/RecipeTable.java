package com.recipe.recipeapp.Database;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;

public class RecipeTable {

    //The columns we'll include in the table
    public static final String TABLE_ITEMS = "items";
    public static final String COL_ID = "recipeID";
    public static final String COL_NAME = "name";
    public static final String COL_DESCRIPTION = "description";
    //public static final String COL_CATEGORY = "category";
    public static final String COL_RATING = "rating";
    //public static final String COL_REVIEW = "review";
    public static final String COL_IMAGE = "image";

    public static final String FTS_VIRTUAL_TABLE = "FTS";

    public static final String FTS_TABLE_CREATE =
            "CREATE VIRTUAL TABLE IF NOT EXISTS " + FTS_VIRTUAL_TABLE +
                    " USING fts3 (" +
                    COL_ID + "TEXT, " +
                    COL_NAME + "TEXT, " +
                    COL_DESCRIPTION + "TEXT, " +
                    //COL_CATEGORY + "LIST, " +
                    COL_RATING + "DOUBLE, " +
                    //COL_REVIEW + "LIST, " +
                    COL_IMAGE + "TEXT" + ")";

    public static final String FTS_TABLE_DELETE =
            "DROP TABLE IF EXISTS " + FTS_VIRTUAL_TABLE;

    public Cursor getWordMatches(String query, String[] columns) {
        String selection = COL_NAME + " MATCH ?";
        String[] selectionArgs = new String[] {query+"*"};

        return query(selection, selectionArgs, columns);
    }

    private Cursor query(String selection, String[] selectionArgs, String[] columns) {
        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
        builder.setTables(FTS_VIRTUAL_TABLE);

        Cursor cursor = builder.query(mDatabaseOpenHelper.getReadableDatabase(),
                columns, selection, selectionArgs, null, null, null);

        if (cursor == null) {
            return null;
        } else if (!cursor.moveToFirst()) {
            cursor.close();
            return null;
        }
        return cursor;
    }

    RecipeTable db = new RecipeTable();
    private void handleIntent(Intent intent) {

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            Cursor c = db.getWordMatches(query, null);
            //process Cursor and display results
        }
    }

}
