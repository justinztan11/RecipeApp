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

    public static String addIngredient(Ingredient ingredient) {
        if (ingredient == null) {
            return null;
        }
        ingredientList.add(ingredient);
        return ingredient.getName();
    }



}
