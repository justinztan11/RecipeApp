package com.recipe.recipeapp.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteQueryBuilder;

import com.recipe.recipeapp.Objects.Ingredient;

import java.util.ArrayList;
import java.util.List;

public class IngredientDataSource {

    private Context mContext;
    SQLiteDatabase mDatabase;
    DatabaseOpenHelper mDbHelper;

    public IngredientDataSource(Context context) {
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

    public void loadData(List<Ingredient> ingredientList) {
        for (Ingredient ingredient: ingredientList) {
            try {
                addWord(ingredient);

//                Log.d("OUTPUT", "loadData: " + recipe.getRecipeID() );
//                Log.d("OUTPUT", "loadData: " + recipe.getName() );
//                Log.d("OUTPUT", "loadData: " + recipe.getDescription());
//                Log.d("OUTPUT", "loadData: " + recipe.getImage() );
//                Log.d("OUTPUT", "loadData: " + recipe.getRating() );
            } catch (SQLiteException e) {
                e.printStackTrace();
            }
        }
    }

    public void addWord(Ingredient ingredient) {
        ContentValues initialValues = new ContentValues(5);

        initialValues.put(IngredientTable.COL_ID, ingredient.getIngredientID());
        initialValues.put(RecipeTable.COL_NAME, ingredient.getName());

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

    public Cursor getWordMatches(String query, String[] columns) {
        String selection = IngredientTable.COL_NAME + " MATCH ?";
        String[] selectionArgs = new String[]{query + "*"};

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


}


