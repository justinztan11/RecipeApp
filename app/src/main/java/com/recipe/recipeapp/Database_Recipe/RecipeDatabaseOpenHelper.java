package com.recipe.recipeapp.Database_Recipe;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.recipe.recipeapp.Database_Ingredient.IngredientTable;

public class RecipeDatabaseOpenHelper extends SQLiteOpenHelper {

    public static final String TAG = "RecipeDatabase";
    private static final String DATABASE_NAME = "DICTIONARY";
    private static final int DATABASE_VERSION = 2;


    public RecipeDatabaseOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(RecipeTable.FTS_TABLE_CREATE);
        //db.execSQL(RecipeTable.FTS_TABLE_INSERT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                + newVersion + ", which will destroy all old data");
        db.execSQL(RecipeTable.FTS_TABLE_DELETE);
        onCreate(db);
    }

//    SQLiteDatabase db;
//
//    String rawQuery = "SELECT * FROM " + RecipeTable.FTS_VIRTUAL_TABLE + " INNER JOIN " + IngredientTable.FTS_VIRTUAL_TABLE
//            + " ON " + RecipeTable.COL_INGREDIENTID + " = " + IngredientTable.COL_ID
//            + " WHERE " + IngredientTable.COL_ID + " = " +  "id";
//    Cursor c = db.rawQuery(rawQuery,null);

}


