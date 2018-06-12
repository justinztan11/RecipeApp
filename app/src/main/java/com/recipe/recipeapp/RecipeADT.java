package com.recipe.recipeapp;

import java.io.StringBufferInputStream;
import java.util.List;

public interface RecipeADT {

    // returns name
    public String getName();

    // returns the image ID
    public String getImageID();

    // returns ingredient list
    public List<String> getIngredients();

    // returns procedure list
    public List<String> getProcedure();

    // returns list of reviews
    public List<Review> getReviews();

    // adds given review to the review list and returns reviewer's username
    public String addReview(Review review);

    // given reviewer's username, removes review from the review list
    public Review removeReview(String username);


}
