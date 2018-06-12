package com.recipe.recipeapp;

import java.util.List;

public interface RecipeADT {

    // returns name
    public String getName();

    // returns the image ID
    public String getImageID();

    // returns ingredient list
    public List<String> getIngredients();

    // retursn procedure list
    public List<String> getProcedure();


}
