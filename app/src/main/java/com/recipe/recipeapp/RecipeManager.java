package com.recipe.recipeapp;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class RecipeManager implements RecipeManagerADT {

    // list of recipe objects
    List<Recipe> RecipeList;

    // maps recipe name/keyword to list of recipes
    HashMap<String, List<Recipe>> RecipeData;

    // given file path, populates both list and map with appropriate recipe data
    public RecipeManager(String filePath) {
    }

    @Override
    public List<Recipe> getRecipe(String name) {
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
    public List<String> findRecipe(Set<String> ingredients) {
        return null;
    }

    @Override
    public void sortRecipeRating() {
        Collections.sort(RecipeList, new Comparator<Recipe>() {
            @Override
            public int compare(Recipe recipe1, Recipe recipe2){
                // add code
                return 0;
            }
        });
    }

    @Override
    public void sortRecipeReviews() {
        Collections.sort(RecipeList, new Comparator<Recipe>() {
            @Override
            public int compare(Recipe recipe1, Recipe recipe2){
                // add code
                return 0;
            }
        });
    }
}
