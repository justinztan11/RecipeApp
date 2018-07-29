package com.recipe.recipeapp.Database_Recipe_Ingredient_Join;

public class JoinTable {

    // table name
    public static final String SQL_TABLE = "JT";

    //The columns in the table
    public static final String COL_RECIPE_ID = "recipeID";
    public static final String COL_INGREDIENT_ID = "ingredientID";

    public static final String[] ALL_COL =
            {COL_RECIPE_ID, COL_INGREDIENT_ID};

    public static final String SQL_TABLE_CREATE =
            "CREATE TABLE IF NOT EXISTS " + SQL_TABLE + " (" +
                    COL_RECIPE_ID + " TEXT, " +
                    COL_INGREDIENT_ID + " TEXT" +
                    ")";

    public static final String SQL_TABLE_DELETE =
            "DROP TABLE IF EXISTS " + SQL_TABLE;
}