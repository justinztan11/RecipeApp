package com.recipe.recipeapp;

import java.util.List;

// Recipe with steps, ingredients, ratings, difficulty level, etc.
public class Recipe implements RecipeADT{
    String name;
    String imageID;
    int rating;
    List<String> ingredients;
    List<String> procedure;

    public Recipe(String name, List<String> ingredients, List<String> procedure) {
        this.name = name;
        this.ingredients = ingredients;
        this.procedure = procedure;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getImageID() {
        return imageID;
    }

    @Override
    public List<String> getIngredients() {
        return ingredients;
    }

    @Override
    public List<String> getProcedure() {
        return procedure;
    }
}
