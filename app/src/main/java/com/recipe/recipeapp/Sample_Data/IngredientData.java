package com.recipe.recipeapp.Sample_Data;
import com.recipe.recipeapp.Objects.Ingredient;
import com.recipe.recipeapp.Objects.Recipe;

import java.util.ArrayList;
import java.util.List;

public final class IngredientData {

    //SortedSet<String> ingredientList;
    public static List<Ingredient> ingredientList = new ArrayList<Ingredient>();

    static {
        addIngredient(new Ingredient("123", "onion"));
        addIngredient(new Ingredient("124", "potato"));
    }

//    // given a filepath, populates list with ingredients from the file
//    public static void populateList(String filePath) {
//
//    }

    // adds ingredient to the database
    public static String addIngredient(Ingredient ingredient) {
        if (ingredient == null) {
            return null;
        }
        ingredientList.add(ingredient);
        return ingredient.getName();
    }

    // removes ingredient from the database
    public static String removeIngredient(Ingredient ingredient) {
        if (ingredient == null) {
            return null;
        }
        ingredientList.remove(ingredient);

        return ingredient.getName();
    }

    // returns true if database contains ingredient
    public static boolean containsIngredient() {
        return !ingredientList.isEmpty();
    }

}
