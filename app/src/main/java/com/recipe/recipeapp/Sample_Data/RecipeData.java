package com.recipe.recipeapp.Sample_Data;

import com.recipe.recipeapp.Objects.Recipe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

public final class RecipeData {

    // list of recipe objects
    public static List<Recipe> recipeList = new ArrayList<Recipe>();

    // provides sample data
    static {
        addRecipe(new Recipe(null, "mac and cheese", "cheesy and maccy",
                null, 2.5f, "noodle", null, null));
        addRecipe(new Recipe(null, "hot dog", "a sausage in a bun",
                null, 1.3f, "meat", null, null));
        addRecipe(new Recipe(null, "pepperoni pizza", "why arent you a fatty",
                null, 2.1f, "bread", null, null));
        addRecipe(new Recipe(null, "cheese pizza", "meaty and cheesy",
                null, 3.1f, "bread", null, null));
        addRecipe(new Recipe(null, "big mac", "better than BK",
                null, 5.0f, "meat", null, null));
        addRecipe(new Recipe(null, "the dog lover", "don't worry, not dog",
                null, 1.2f, "meat", null, null));

    }

    public static String addRecipe(Recipe recipe) {
        if (recipe == null) {
            return null;
        }
        recipeList.add(recipe);

        return recipe.getName();
    }

    // sorts recipes based on alphabet
    public void sortRecipeAlpha() {
        Collections.sort(recipeList, new Comparator<Recipe>() {
            @Override
            public int compare(Recipe recipe1, Recipe recipe2){
                return recipe1.getName().compareTo(recipe2.getName());
            }
        });
    }

    // sorts recipes based on rating
    public void sortRecipeRating() {
        Collections.sort(recipeList, new Comparator<Recipe>() {
            @Override
            public int compare(Recipe recipe1, Recipe recipe2){
                // add code
                return 0;
            }
        });
    }

    // sorts recipes based on number of reviews (i.e. popularity)
    public void sortRecipeReviews() {
        Collections.sort(recipeList, new Comparator<Recipe>() {
            @Override
            public int compare(Recipe recipe1, Recipe recipe2){
                // add code
                return 0;
            }
        });
    }
}
