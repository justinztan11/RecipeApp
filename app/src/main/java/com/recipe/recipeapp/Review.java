package com.recipe.recipeapp;

public class Review implements ReviewADT{

    String username;
    String comment;
    int rating;

    // creates a review with comment, rating, and difficulty
    public Review(String username, String comment, int rating) {

    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public String getComment() {
        return null;
    }

    @Override
    public int getRating() {
        return 0;
    }
}

