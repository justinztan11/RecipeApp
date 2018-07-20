package com.recipe.recipeapp.Database_Ingredient;

public class IngredientTable {

    // table name
    public static final String FTS_VIRTUAL_TABLE = "FTS";

    //The columns in the table
    public static final String COL_ID = "ingredientID";
    public static final String COL_NAME = "name";

    public static final String[] ALL_COL =
            {COL_ID, COL_NAME};

    public static final String FTS_TABLE_CREATE =
            "CREATE VIRTUAL TABLE IF NOT EXISTS " + FTS_VIRTUAL_TABLE +
                    " USING fts3 (" +
                    COL_ID + " TEXT PRIMARY KEY, " +
                    COL_NAME + " TEXT" +
                     ")";

    public static final String FTS_TABLE_DELETE =
            "DROP TABLE IF EXISTS " + FTS_VIRTUAL_TABLE;

}
