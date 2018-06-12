package com.recipe.recipeapp;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class RecipeManager implements RecipeManagerADT {

    // list of recipe names
    List<String> RecipeNames;

    // maps recipe name to recipe object
    HashMap<String, Recipe> RecipeData;

    // given file path, populates both list and map with appropriate recipe data
    public RecipeManager(String filePath) {
    }

    @Override
    public List<String> findRecipe(Set<String> ingredients) {
        return null;
    }

    @Override
    public String addRecipe(Recipe recipe) {
        return null;
    }

    @Override
    public String removeRecipe(Recipe recipe) {
        return null;
    }

    @Override
    public boolean containsRecipe(Recipe recipe) {
        return false;
    }

    @Override
    public void sortRecipeRating() {

    }

    @Override
    public void sortRecipeReviews() {

    }
}
