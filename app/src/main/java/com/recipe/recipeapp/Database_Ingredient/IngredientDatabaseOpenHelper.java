package com.recipe.recipeapp.Database_Ingredient;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class IngredientDatabaseOpenHelper extends SQLiteOpenHelper {

    public static final String TAG = "IngredientDatabase";
    private static final String DATABASE_NAME = "INGREDIENTS";
    private static final int DATABASE_VERSION = 1;


    public IngredientDatabaseOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(IngredientTable.FTS_TABLE_CREATE);
        //db.execSQL(IngredientTable.FTS_TABLE_INSERT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                + newVersion + ", which will destroy all old data");
        db.execSQL(IngredientTable.FTS_TABLE_DELETE);
        onCreate(db);
    }
}


