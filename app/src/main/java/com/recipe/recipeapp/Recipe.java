package com.recipe.recipeapp;

import java.util.ArrayList;
import java.util.List;

// Recipe with steps, ingredients, ratings, difficulty level, etc.
public class Recipe implements RecipeADT{
    String name;
    String imageID;
    List<String> ingredients;
    List<String> procedure;
    List<Review> reviews;


    public Recipe(String name, String imageID, List<String> ingredients, List<String> procedure) {
        this.name = name;
        this.imageID = imageID;
        this.ingredients = ingredients;
        this.procedure = procedure;
        this.reviews = new ArrayList<>();
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

    @Override
    public List<Review> getReviews() {
        return null;
    }

    @Override
    public String addReview(Review review) {
        return null;
    }

    @Override
    public Review removeReview(String username) {
        return null;
    }
}
