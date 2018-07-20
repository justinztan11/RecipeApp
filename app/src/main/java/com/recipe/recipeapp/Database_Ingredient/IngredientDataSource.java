package com.recipe.recipeapp.Database_Ingredient;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteQueryBuilder;


import com.recipe.recipeapp.Objects.Ingredient;

import java.util.ArrayList;
import java.util.List;

public class IngredientDataSource {

    private Context mContext;
    SQLiteDatabase mDatabase;
    IngredientDatabaseOpenHelper mDbHelper;

    public IngredientDataSource(Context context) {
        this.mContext = context;
        mDbHelper = new IngredientDatabaseOpenHelper(mContext);
        mDatabase = mDbHelper.getWritableDatabase();
    }

    public void open() {
        mDbHelper.getWritableDatabase();
    }

    public void close() {
        mDbHelper.close();
    }

    public void loadData(List<Ingredient> ingredientList) {
        for (Ingredient ingredient: ingredientList) {
            try {
                addIngredient(ingredient);
            } catch (SQLiteException e) {
                e.printStackTrace();
            }
        }
    }

    public void addIngredient(Ingredient ingredient) {
        ContentValues initialValues = new ContentValues(2);

        initialValues.put(IngredientTable.COL_ID, ingredient.getIngredientID());
        initialValues.put(IngredientTable.COL_NAME, ingredient.getName());

        mDatabase.insert(IngredientTable.FTS_VIRTUAL_TABLE, null, initialValues);
    }

    public void deleteAll() {
        mDatabase.delete(IngredientTable.FTS_VIRTUAL_TABLE, null, null);
    }

    public List<Ingredient> getAll() {
        List<Ingredient> ingredientList = new ArrayList<>();
        Cursor cursor = mDatabase.query(IngredientTable.FTS_VIRTUAL_TABLE, IngredientTable.ALL_COL,
                null, null, null, null, null);

        while(cursor.moveToNext()) {
            Ingredient ingredient = new Ingredient();
            ingredient.setIngredientID(cursor.getString(cursor.getColumnIndex(IngredientTable.COL_ID)));
            ingredient.setName(cursor.getString(cursor.getColumnIndex(IngredientTable.COL_NAME)));
            ingredientList.add(ingredient);
        }

        return ingredientList;
    }

    public Cursor getIngredientMatches(String query, String[] columns) {

        String selection = null;
        String[] selectionArgs = null;
        if (query != null) {
            selection = IngredientTable.COL_NAME + " MATCH ?";
            selectionArgs = new String[]{query + "*"};
        }

        return query(selection, selectionArgs, columns);
    }

    private Cursor query(String selection, String[] selectionArgs, String[] columns) {
        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
        builder.setTables(IngredientTable.FTS_VIRTUAL_TABLE);

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
        return DatabaseUtils.queryNumEntries(mDatabase, IngredientTable.FTS_VIRTUAL_TABLE);
    }
}


