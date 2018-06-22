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
    public static final String COL_IMAGE = "image";
    //public static final String COL_CATEGORY = "category";
    public static final String COL_RATING = "rating";
    //public static final String COL_REVIEW = "review";


    public static final String FTS_VIRTUAL_TABLE = "FTS";

    public static final String FTS_TABLE_CREATE =
            "CREATE VIRTUAL TABLE IF NOT EXISTS " + FTS_VIRTUAL_TABLE +
                    " USING fts3 (" +
                    COL_ID + ", " +
                    COL_NAME + ", " +
                    COL_DESCRIPTION + ", " +
                    //COL_CATEGORY + "LIST, " +
                    COL_IMAGE + ", " +
                    COL_RATING + ", " +
                    //COL_REVIEW + "LIST, " +
                     ")";

    public static final String FTS_TABLE_DELETE =
            "DROP TABLE IF EXISTS " + FTS_VIRTUAL_TABLE;

}
