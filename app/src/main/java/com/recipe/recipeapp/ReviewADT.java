package com.recipe.recipeapp;

public interface ReviewADT {

    // returns the reviewer's username
    public String getUsername();

    // returns the comment of the review
    public String getComment();

    // returns the rating of the review
    public int getRating();
}
