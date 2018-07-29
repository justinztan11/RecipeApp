package com.recipe.recipeapp.Objects;

import java.util.UUID;

public class Ingredient {
    private String ingredientID;
    private String name;

    public Ingredient() {
        this(null, null);}

    public Ingredient(String name) {
        this(null, name);}

    public Ingredient(String ingredientID, String name) {

        if (ingredientID == null) {
            ingredientID = UUID.randomUUID().toString();
        }

        this.ingredientID = ingredientID;
        this.name = name;
    }

    public String getIngredientID() {
        return ingredientID;
    }

    public void setIngredientID(String ingredientID) {
        this.ingredientID = ingredientID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

