package com.recipe.recipeapp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class RecipeManager implements RecipeManagerADT {

    // list of recipe objects
    List<Recipe> recipeList;

    // maps recipe name/keyword to list of recipes
//    HashMap<String, List<Recipe>> recipeData;

    // given file path, populates both list and map with appropriate recipe data
    public RecipeManager(String filePath) {
        //initialize instance vars
        recipeList = new ArrayList<Recipe>();
//        recipeData = new HashMap<String, List<Recipe>>();


        //Grabbing recipes
        //converting recipes

    }

    public List<String> findRecipe(Set<String> ingredients) {
        return null;
    }

    @Override
    public List<Recipe> getRecipe(String name) {
        return null;
    }

    @Override
    public String addRecipe(Recipe recipe) {
        if (recipe == null) {
            return null;
        }

        recipeList.add(recipe);
//        recipeData.put(recipe.getName(), recipe);

        return recipe.getName();
    }

    @Override
    public String removeRecipe(Recipe recipe) {
        if (recipe == null) {
            return null;
        }

        recipeList.remove(recipe);
//        recipeData.remove(recipe.getName());

        return recipe.getName();
    }

    @Override
    public boolean containsRecipe(Recipe recipe) {
//        return recipeData.containsValue(recipe);
        return false;
    }

    @Override
    public void sortRecipeRating() {
        Collections.sort(recipeList, new Comparator<Recipe>() {
            @Override
            public int compare(Recipe recipe1, Recipe recipe2){
                // add code
                return 0;
            }
        });
    }

    @Override
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
