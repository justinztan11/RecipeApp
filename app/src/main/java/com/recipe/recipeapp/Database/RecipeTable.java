package com.recipe.recipeapp.Database;

public class RecipeTable {

    // table name
    public static final String FTS_VIRTUAL_TABLE = "RPT";

    //The columns in the table
    public static final String COL_ID = "recipeID";
    public static final String COL_NAME = "recipeName";
    public static final String COL_DESCRIPTION = "description";
    public static final String COL_IMAGE = "image";
    public static final String COL_RATING = "rating";
    public static final String COL_INGREDIENT_IDS = "ingredientIDs";
    public static final String COL_CATEGORY = "category";
    //public static final String COL_REVIEW = "review";

    public static final String[] ALL_COL =
            {COL_ID, COL_NAME, COL_DESCRIPTION, COL_IMAGE, COL_RATING, COL_CATEGORY};

    public static final String FTS_TABLE_CREATE =
            "CREATE VIRTUAL TABLE IF NOT EXISTS " + FTS_VIRTUAL_TABLE +
                    " USING fts3 (" +
                    COL_ID + " TEXT PRIMARY KEY, " +
                    COL_NAME + " TEXT, " +
                    COL_DESCRIPTION + " TEXT, " +
                    COL_IMAGE + " TEXT, " +
                    COL_RATING + " DOUBLE, " +
//                    COL_INGREDIENT_IDS + "TEXT" +
                    COL_CATEGORY + " TEXT" +
                    //COL_REVIEW + "LIST, " +
                     ")";

    public static final String FTS_TABLE_DELETE =
            "DROP TABLE IF EXISTS " + FTS_VIRTUAL_TABLE;

//    public static final String FTS_TABLE_INSERT_SAMPLE =
//            "INSERT INTO FTS(" +
//                    COL_ID + " TEXT PRIMARY KEY, " +
//                    COL_NAME + " TEXT, " +
//                    COL_DESCRIPTION + " TEXT, " +
//                    //COL_CATEGORY + "LIST, " +
//                    COL_IMAGE + " TEXT, " +
//                    COL_RATING + " DOUBLE) " +
//                    "VALUES('ID', 'MAC', 'NO DESCRIPTION', 'NO IMGE', 6.0)";

}
