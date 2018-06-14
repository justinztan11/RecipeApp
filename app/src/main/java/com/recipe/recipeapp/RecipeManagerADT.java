package com.recipe.recipeapp;

import java.util.List;
import java.util.Set;

public interface RecipeManagerADT {

    // returns a list of recipes given name/keyword
    public List<Recipe> getRecipe(String name);

    // adds recipe to recipe database and returns the recipe name
    public String addRecipe(Recipe recipe);

    // removes recipe from recipe database and returns the recipe name
    public String removeRecipe(Recipe recipe);

    // checks if recipe exists, returns true if it does, false otherwise
    public boolean containsRecipe(Recipe recipe);

    // returns a list of all the recipes with the following ingredients
    public List<String> findRecipe(Set<String> ingredients);

    // sorts recipes based on rating
    public void sortRecipeRating();

    // sorts recipes based on number of reviews (i.e. popularity)
    public void sortRecipeReviews();

}
