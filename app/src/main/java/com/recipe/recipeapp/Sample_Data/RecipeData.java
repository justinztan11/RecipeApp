package com.recipe.recipeapp.Sample_Data;

import com.recipe.recipeapp.Objects.Ingredient;
import com.recipe.recipeapp.Objects.Recipe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

public final class RecipeData {

    // list of recipe objects
    public static List<Recipe> recipeList = new ArrayList<Recipe>();

    // sample sets of ingredients for a specific recipe
    public static List<String> pizzaIngredients = new ArrayList<>();
    public static List<String> noodleIngredients = new ArrayList<>();

    // provides sample data
    static {

        pizzaIngredients.add("salt");
        pizzaIngredients.add("bread");
        pizzaIngredients.add("cheese");

        noodleIngredients.add("noodle");
        noodleIngredients.add("salt");
        noodleIngredients.add("cheese");


        addRecipe(new Recipe(null, "mac and cheese", "cheesy and maccy",
                null, 2.5f, "noodle", noodleIngredients, null));

        addRecipe(new Recipe(null, "hot dog", "a sausage in a bun",
                null, 1.3f, "meat", null, null));

        addRecipe(new Recipe(null, "pepperoni pizza", "why arent you a fatty",
                null, 2.1f, "bread", pizzaIngredients, null));

        addRecipe(new Recipe(null, "cheese pizza", "meaty and cheesy",
                null, 3.1f, "bread", pizzaIngredients, null));

        addRecipe(new Recipe(null, "big mac", "better than BK",
                null, 3.0f, "meat", null, null));

        addRecipe(new Recipe(null, "the dog lover", "don't worry, not dog",
                null, 3.5f, "meat", null, null));

        addRecipe(new Recipe(null, "spaghetti", "with pasty tomatoes",
                null, 0.6f, "noodle", noodleIngredients, null));

        addRecipe(new Recipe(null, "ramen", "better than instant",
                null, 4.3f, "noodle", noodleIngredients, null));

        addRecipe(new Recipe(null, "beef noodle soup", "beefy",
                null, 4.0f, "noodle", noodleIngredients, null));

        addRecipe(new Recipe(null, "orange chicken", "authentic americanized chinese",
                null, 2.0f, "meat", null, null));

        addRecipe(new Recipe(null, "fried chicken", "crispy goodness",
                null, 5.0f, "meat", null, null));

        addRecipe(new Recipe(null, "garlic bread", "stale",
                null, 0.1f, "bread", null, null));

    }

    public static String addRecipe(Recipe recipe) {
        if (recipe == null) {
            return null;
        }
        recipeList.add(recipe);

        return recipe.getName();
    }
}
