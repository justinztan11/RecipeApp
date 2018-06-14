package com.recipe.recipeapp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class RecipeManager implements RecipeManagerADT {

    // list of recipe names
    List<String> recipeNames;

    // maps recipe name to recipe object
    HashMap<String, Recipe> recipeData;

    // given file path, populates both list and map with appropriate recipe data
    public RecipeManager(String filePath) {
        //initialize instance vars
        recipeNames = new ArrayList<String>();
        recipeData = new HashMap<String, Recipe>();


        //Grabbing recipes
        //converting recipes

    }


    @Override
    public List<String> findRecipe(Set<String> ingredients) {
        return null;
    }

    @Override
    public String addRecipe(Recipe recipe) {
        if (recipe == null) {
            return null;
        }

        recipeNames.add(recipe.getName());
        recipeData.put(recipe.getName(), recipe);

        return recipe.getName();
    }

    @Override
    public String removeRecipe(Recipe recipe) {
        if (recipe == null) {
            return null;
        }

        recipeNames.remove(recipe.getName());
        recipeData.remove(recipe.getName());

        return recipe.getName();
    }

    @Override
    public boolean containsRecipe(Recipe recipe) {
        return recipeData.containsValue(recipe);
    }

    @Override
    public void sortRecipeRating() {
        
    }

    @Override
    public void sortRecipeReviews() {

    }
}
