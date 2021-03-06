package com.recipe.recipeapp.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;

import com.recipe.recipeapp.MainActivity;
import com.recipe.recipeapp.Objects.Recipe;
import com.recipe.recipeapp.Singleton.CategorySelectedSingleton;

import java.util.ArrayList;
import java.util.List;

public class RecipeDataSource {

    private Context mContext;

    private SQLiteDatabase mDatabase;
    private DatabaseOpenHelper mDbHelper;

    public RecipeDataSource(Context context) {
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

    public void loadData(List<Recipe> recipeList) {
        for (Recipe recipe : recipeList) {
            try {
                addRecipe(recipe);
                Log.d("OUTPUT", "loadData: " + recipe.getRecipeID());
                Log.d("OUTPUT", "loadData: " + recipe.getName());
                Log.d("OUTPUT", "loadData: " + recipe.getDescription());
                Log.d("OUTPUT", "loadData: " + recipe.getImage());
                Log.d("OUTPUT", "loadData: " + recipe.getRating());
                Log.d("OUTPUT", "loadData: " + recipe.getCategory());
            } catch (SQLiteException e) {
                e.printStackTrace();
            }
        }
    }

    public void addRecipe(Recipe recipe) {

        // ADD TO RECIPE TABLE
        ContentValues recipeValues = new ContentValues(5);

        recipeValues.put(RecipeTable.COL_ID, recipe.getRecipeID());
        recipeValues.put(RecipeTable.COL_NAME, recipe.getName());
        recipeValues.put(RecipeTable.COL_DESCRIPTION, recipe.getDescription());
        recipeValues.put(RecipeTable.COL_IMAGE, recipe.getImage());
        recipeValues.put(RecipeTable.COL_RATING, recipe.getRating());
        recipeValues.put(RecipeTable.COL_CATEGORY, recipe.getCategory());
        //recipeValues.put(RecipeTable.COL_REVIEW, recipe.getReviews());

        mDatabase.insert(RecipeTable.FTS_VIRTUAL_TABLE, null, recipeValues);


        // ADD TO JOIN TABLE
        List<String> ingredients = recipe.getIngredients();

        if (ingredients != null) {
            for (String ingredient : ingredients) {
                ContentValues joinValues = new ContentValues(2);

                IngredientDataSource mIngredientDataSource = MainActivity.mIngredientDataSource;
                Cursor cursor = mIngredientDataSource.getIngredientMatches(ingredient, null);
                String ingredientID = mIngredientDataSource.getResultsList(cursor)
                        .get(0).getIngredientID();

                joinValues.put(JoinTable.COL_RECIPE_ID, recipe.getRecipeID());
                joinValues.put(JoinTable.COL_INGREDIENT_ID, ingredientID);

                mDatabase.insert(JoinTable.SQL_TABLE, null, joinValues);
            }
        }

    }

    public void deleteAll() {
        mDatabase.delete(RecipeTable.FTS_VIRTUAL_TABLE, null, null);
        mDatabase.delete(JoinTable.SQL_TABLE, null, null);
    }

    public List<Recipe> getResultsList(Cursor cursor) {

        List<Recipe> tempList = new ArrayList<>();

        if (cursor != null && cursor.getCount() != 0) {
            try {
                // cursor starts at index 0, needs to execute below block only once before moving
                do {
                    Recipe recipe = new Recipe();
                    recipe.setRecipeID(cursor.getString(cursor.getColumnIndex(RecipeTable.COL_ID)));
                    recipe.setName(cursor.getString(cursor.getColumnIndex(RecipeTable.COL_NAME)));
                    recipe.setDescription(cursor.getString(cursor.getColumnIndex(RecipeTable.COL_DESCRIPTION)));
                    recipe.setImage(cursor.getString(cursor.getColumnIndex(RecipeTable.COL_IMAGE)));
                    recipe.setRating(cursor.getFloat(cursor.getColumnIndex(RecipeTable.COL_RATING)));
                    tempList.add(recipe);
                    Log.d("searchOutput", "Search Output: " + recipe.getName());

                } while (cursor.moveToNext());

            } finally {
                cursor.close();
            }
        }

        return tempList;
    }

    public Cursor getRecipeMatches(String query, String[] columns) {

        // the category selected from the search page
        String categorySelected = CategorySelectedSingleton.getInstance()
                .categorySelected.toLowerCase();
        Log.d("Category Selected", "getRecipeMatches - Category: " + categorySelected);

        String selection = RecipeTable.FTS_VIRTUAL_TABLE + " MATCH ?";
        String[] selectionArgs;
        if (query == null) {
            if (categorySelected.equals("all")) {
                selection = null;
                selectionArgs = null;
                columns = RecipeTable.ALL_COL;
            } else {
                selectionArgs = new String[]{RecipeTable.COL_CATEGORY + ":" + categorySelected};
            }
        } else {
            if (categorySelected.equals("all")) {
                selectionArgs = new String[]{RecipeTable.COL_NAME + ":" + query + "* OR "
                        + RecipeTable.COL_DESCRIPTION + ":" + query + "*"};
            } else {
                selection = RecipeTable.COL_CATEGORY + " MATCH ?";
                selectionArgs = new String[]{RecipeTable.COL_NAME + ":" + query + "* OR "
                        + RecipeTable.COL_DESCRIPTION + ":" + query + "*" + categorySelected};
            }
        }

        return query(selection, selectionArgs, columns);
    }

    private Cursor query(String selection, String[] selectionArgs, String[] columns) {
        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
        builder.setTables(RecipeTable.FTS_VIRTUAL_TABLE);

        Cursor cursor = builder.query(mDbHelper.getReadableDatabase(),
                columns, selection, selectionArgs, null, null, RecipeTable.COL_NAME);

        if (cursor == null) {
            return null;
        } else if (!cursor.moveToFirst()) {
            cursor.close();
            return null;
        }
        return cursor;
    }

    public Cursor getRecipeFromIngredients(List<String> ingredients) {

        int ingredientsListSize = ingredients.size();
        String tempTableName;

        // construct sql table for recipes found from each ingredient
        for (int i = 0; i < ingredientsListSize; i++) {
            tempTableName = "TEMP" + i;
            mDatabase.execSQL("DROP TABLE IF EXISTS " + tempTableName);
            mDatabase.execSQL(constructTable(tempTableName, ingredients.get(i)));
        }

        String allTableNames = "";
        String queryIntersection = "";
        for (int i = 0; i < ingredientsListSize; i++) {
            tempTableName = "TEMP" + i;
            if (i != ingredientsListSize - 1) {
                allTableNames += tempTableName + ", ";
                queryIntersection += "TEMP0." + RecipeTable.COL_ID + " = " + tempTableName + "." + RecipeTable.COL_ID + " AND ";
            } else {
                allTableNames += tempTableName;
                queryIntersection += "TEMP0." + RecipeTable.COL_ID + " = " + tempTableName + "." + RecipeTable.COL_ID;
            }
        }
        

        String fullSearchQuery = "SELECT * FROM " + allTableNames + " WHERE " + queryIntersection;

        Cursor c = mDatabase.rawQuery(fullSearchQuery, null);
        c.moveToFirst();

        return c;
    }

    private String constructTable(String tableName, String ingredient) {

        // Implied join - not using JOIN statement

//        String rawQuery =
//
//              "SELECT * FROM " + RecipeTable.FTS_VIRTUAL_TABLE +
//                      ", " + JoinTable.SQL_TABLE +
//                      ", " + IngredientTable.FTS_VIRTUAL_TABLE +
//
//              " WHERE " + RecipeTable.FTS_VIRTUAL_TABLE + "." + RecipeTable.COL_ID +
//                      " = " + JoinTable.SQL_TABLE + "." + JoinTable.COL_RECIPE_ID +
//
//              " AND " + JoinTable.SQL_TABLE + "." + JoinTable.COL_INGREDIENT_ID +
//                      " = " + IngredientTable.FTS_VIRTUAL_TABLE + "." + IngredientTable.COL_ID +
//
//              " AND " + IngredientTable.FTS_VIRTUAL_TABLE + "." + IngredientTable.COL_NAME +
//                      " MATCH " + "'" + ingredient + "'";

        // JOIN Recipe table, Join table, and Ingredient table

        String rawQuery =

                "CREATE TABLE " + tableName + " AS" +

                        " SELECT * FROM " + RecipeTable.FTS_VIRTUAL_TABLE +

                        " INNER JOIN " + JoinTable.SQL_TABLE +
                        " ON " + RecipeTable.FTS_VIRTUAL_TABLE + "." + RecipeTable.COL_ID +
                        " = " + JoinTable.SQL_TABLE + "." + JoinTable.COL_RECIPE_ID +

                        " INNER JOIN " + IngredientTable.FTS_VIRTUAL_TABLE +
                        " ON " + JoinTable.SQL_TABLE + "." + JoinTable.COL_INGREDIENT_ID +
                        " = " + IngredientTable.FTS_VIRTUAL_TABLE + "." + IngredientTable.COL_ID +

                        " AND " + IngredientTable.FTS_VIRTUAL_TABLE + "." + IngredientTable.COL_NAME +
                        " MATCH " + "'" + ingredient + "'";

        return rawQuery;
    }

    public long getDataItemsCount() {
        return DatabaseUtils.queryNumEntries(mDatabase, RecipeTable.FTS_VIRTUAL_TABLE);
    }

}


