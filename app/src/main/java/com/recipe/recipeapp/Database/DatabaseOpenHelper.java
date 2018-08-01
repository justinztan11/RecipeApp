package com.recipe.recipeapp.Database;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseOpenHelper extends SQLiteOpenHelper {

    public static final String TAG = "Database";
    private static final String DATABASE_NAME = "DATABASE";
    private static final int DATABASE_VERSION = 8;


    public DatabaseOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(RecipeTable.FTS_TABLE_CREATE);
        db.execSQL(IngredientTable.FTS_TABLE_CREATE);
        db.execSQL(JoinTable.SQL_TABLE_CREATE);
        //db.execSQL(RecipeTable.FTS_TABLE_INSERT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                + newVersion + ", which will destroy all old data");
        db.execSQL(RecipeTable.FTS_TABLE_DELETE);
        db.execSQL(IngredientTable.FTS_TABLE_DELETE);
        db.execSQL(JoinTable.SQL_TABLE_DELETE);

        onCreate(db);
    }


}


