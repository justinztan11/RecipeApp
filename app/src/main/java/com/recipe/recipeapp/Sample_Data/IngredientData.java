package com.recipe.recipeapp.Sample_Data;
import com.recipe.recipeapp.Objects.Ingredient;
import com.recipe.recipeapp.Objects.Recipe;

import java.util.ArrayList;
import java.util.List;

public final class IngredientData {

    //SortedSet<String> ingredientList;
    public static List<Ingredient> ingredientList = new ArrayList<Ingredient>();

    static {
        addIngredient(new Ingredient(null, "onion"));
        addIngredient(new Ingredient(null, "potato"));
        addIngredient(new Ingredient(null, "salt"));
        addIngredient(new Ingredient(null, "noodle"));
        addIngredient(new Ingredient(null, "beef"));
        addIngredient(new Ingredient(null, "bread"));
        addIngredient(new Ingredient(null, "cheese"));

    }

    public static String addIngredient(Ingredient ingredient) {
        if (ingredient == null) {
            return null;
        }
        ingredientList.add(ingredient);
        return ingredient.getName();
    }

}
