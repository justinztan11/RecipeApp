package com.recipe.recipeapp.SampleData;

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
                null, 2.5f, null, null, null));
        addRecipe(new Recipe(null, "hot dog", "a sausage in a bun",
                null, 1.3f, null, null, null));
        addRecipe(new Recipe(null, "pizza", "why arent you a fatty",
                null, 2.1f, null, null, null));
    }

//    // given file path, populates list appropriate recipe data
//    public static void populateList(String filePath) {
//    }


    // returns a list of all the recipes with the following ingredients
    public static List<String> findRecipe(Set<String> ingredients) {
        return null;
    }

    // returns the list of recipes
    public static List<Recipe> getRecipe(String name) {
        return null;
    }

    // adds recipe to recipe database and returns the recipe name
    public static String addRecipe(Recipe recipe) {
        if (recipe == null) {
            return null;
        }
        recipeList.add(recipe);

        return recipe.getName();
    }

    // removes recipe from recipe database and returns the recipe name
    public static String removeRecipe(Recipe recipe) {
        if (recipe == null) {
            return null;
        }
        recipeList.remove(recipe);

        return recipe.getName();
    }

    // checks if recipe exists, returns true if it does, false otherwise
    public static boolean containsRecipe(Recipe recipe) {
        return recipeList.contains(recipe);
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
