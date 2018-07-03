package com.recipe.recipeapp.Database_Ingredient;

public class IngredientTable {

    // table name
    public static final String FTS_VIRTUAL_TABLE = "FTS";

    //The columns in the table
    public static final String COL_ID = "IngredientID";
    public static final String COL_NAME = "name";

    public static final String[] ALL_COL =
            {COL_ID, COL_NAME};

    public static final String FTS_TABLE_CREATE =
            "CREATE VIRTUAL TABLE IF NOT EXISTS " + FTS_VIRTUAL_TABLE +
                    " USING fts3 (" +
                    COL_ID + " TEXT PRIMARY KEY, " +
                    COL_NAME + " TEXT, " +
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
