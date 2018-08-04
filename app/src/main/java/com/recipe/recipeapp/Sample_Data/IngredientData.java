package com.recipe.recipeapp.Sample_Data;
import com.recipe.recipeapp.Objects.Ingredient;
import com.recipe.recipeapp.Objects.Recipe;

import java.util.ArrayList;
import java.util.List;

public final class IngredientData {

    //SortedSet<String> ingredientList;
    public static List<Ingredient> ingredientList = new ArrayList<Ingredient>();

    static {

        addIngredient(new Ingredient("onion"));
        addIngredient(new Ingredient("potato"));
        addIngredient(new Ingredient("salt"));
        addIngredient(new Ingredient("noodle"));
        addIngredient(new Ingredient("beef"));
        addIngredient(new Ingredient("bread"));
        addIngredient(new Ingredient("cheese"));
        addIngredient(new Ingredient("blood"));
        addIngredient(new Ingredient("garlic"));
        addIngredient(new Ingredient("meat"));

    }

    public static String addIngredient(Ingredient ingredient) {
        if (ingredient == null) {
            return null;
        }
        ingredientList.add(ingredient);
        return ingredient.getName();
    }

}
