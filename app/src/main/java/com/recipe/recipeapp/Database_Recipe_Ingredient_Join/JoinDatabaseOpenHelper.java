package com.recipe.recipeapp.Database_Recipe_Ingredient_Join;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class JoinDatabaseOpenHelper extends SQLiteOpenHelper {

    public static final String TAG = "JoinDatabase";
    private static final String DATABASE_NAME = "JOIN";
    private static final int DATABASE_VERSION = 1;


    public JoinDatabaseOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(JoinTable.SQL_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                + newVersion + ", which will destroy all old data");
        db.execSQL(JoinTable.SQL_TABLE_DELETE);
        onCreate(db);
    }

}