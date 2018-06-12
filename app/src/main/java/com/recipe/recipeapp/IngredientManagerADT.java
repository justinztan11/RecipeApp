package com.recipe.recipeapp;

public interface IngredientManagerADT {

    // adds ingredient to the database
    public String addIngredient();

    // removes ingredient from the database
    public String removeIngredient();

    // returns true if database contains ingredient
    public boolean containsIngredient();
}