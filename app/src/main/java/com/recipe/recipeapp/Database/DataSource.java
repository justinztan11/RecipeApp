package com.recipe.recipeapp.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

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
}


