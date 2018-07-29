package com.recipe.recipeapp.Database_Recipe;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;

import com.recipe.recipeapp.Database_Ingredient.IngredientDataSource;
import com.recipe.recipeapp.Database_Ingredient.IngredientDatabaseOpenHelper;
import com.recipe.recipeapp.Database_Recipe_Ingredient_Join.JoinDatabaseOpenHelper;
import com.recipe.recipeapp.Database_Recipe_Ingredient_Join.JoinTable;
import com.recipe.recipeapp.MainActivity;
import com.recipe.recipeapp.Objects.Recipe;
import com.recipe.recipeapp.Singleton.CategorySelectedSingleton;

import java.util.ArrayList;
import java.util.List;

public class RecipeDataSource {

    private Context mContext;

    private SQLiteDatabase mDatabase;
    private RecipeDatabaseOpenHelper mDbHelper;

    private SQLiteDatabase jDatabase;
    private JoinDatabaseOpenHelper jDbHelper;

    public RecipeDataSource(Context context) {
        this.mContext = context;

        mDbHelper = new RecipeDatabaseOpenHelper(mContext);
        mDatabase = mDbHelper.getWritableDatabase();

        jDbHelper = new JoinDatabaseOpenHelper(mContext);
        jDatabase = jDbHelper.getWritableDatabase();

    }

    public void open() {
        mDbHelper.getWritableDatabase();
    }

    public void close() {
        mDbHelper.close();
    }

    public void loadData(List<Recipe> recipeList) {
        for (Recipe recipe: recipeList) {
            try {
                addRecipe(recipe);
                Log.d("OUTPUT", "loadData: " + recipe.getRecipeID() );
                Log.d("OUTPUT", "loadData: " + recipe.getName() );
                Log.d("OUTPUT", "loadData: " + recipe.getDescription());
                Log.d("OUTPUT", "loadData: " + recipe.getImage() );
                Log.d("OUTPUT", "loadData: " + recipe.getRating() );
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

                jDatabase.insert(JoinTable.SQL_TABLE, null, joinValues);
            }
        }

    }

    public void deleteAll() {
        mDatabase.delete(RecipeTable.FTS_VIRTUAL_TABLE, null, null);
    }

    public List<Recipe> getResultsList(Cursor cursor) {

        List<Recipe> tempList = new ArrayList<>();

        if (cursor != null) {
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

    //    private Cursor getRecipeFromIngredients(List<String> ingredients, String[] columns) {
//
//    }

    public long getDataItemsCount() {
        return DatabaseUtils.queryNumEntries(mDatabase, RecipeTable.FTS_VIRTUAL_TABLE);
    }

}


