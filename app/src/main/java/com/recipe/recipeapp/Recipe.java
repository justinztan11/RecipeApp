package com.recipe.recipeapp;

import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

// Recipe with steps, ingredients, ratings, difficulty level, etc.
public class Recipe implements RecipeADT {
    private String recipeID;
    private String name;
    private String description;
    private String image;
    private double rating;
    private List<String> category;
    private List<String> ingredients;
    private List<String> procedure;
    private List<Review> reviews;


    public Recipe(String recipeID, String name, String description, String image, double rating, List<String> category,  List<String> ingredients, List<String> procedure) {
        this.recipeID = recipeID;
        this.name = name;
        this.description = description;
        this.image = image;
        this.rating = rating;
        this.category = category;
        this.ingredients = ingredients;
        this.procedure = procedure;
        this.reviews = new ArrayList<>();
    }

    @Override
    public String getItemID() {
        return null;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public List<String> getCategory() {
        return null;
    }

    @Override
    public String getImage() {
        return image;
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

    @Override
    public double getRating() {
        return 0;
    }


}
