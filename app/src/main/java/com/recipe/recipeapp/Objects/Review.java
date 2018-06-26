package com.recipe.recipeapp.Objects;

public class Review {

    private String username;
    private String comment;
    private int rating;

    // creates a review with comment, rating, and difficulty
    public Review(String username, String comment, int rating) {

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}

