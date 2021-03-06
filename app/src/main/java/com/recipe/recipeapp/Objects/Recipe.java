package com.recipe.recipeapp.Objects;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

// Recipe with steps, ingredients, ratings, difficulty level, etc.
public class Recipe {
    private String recipeID;
    private String name;
    private String description;
    private String image;
    private float rating;
    private String category;
    private List<String> ingredients;
    private List<String> procedure;
    private List<Review> reviews;

    public Recipe() {
        this(null, null, null, null, 0, null, null, null);
    }

    public Recipe(String name) {
        this(null, name, null, null, 0, null, null, null);
    }

    public Recipe(String name, String description, String image, float rating, String category,  List<String> ingredients, List<String> procedure) {
        this(null, name, description, image, rating, category, ingredients, procedure);
    }

    public Recipe(String recipeID, String name, String description, String image, float rating, String category,  List<String> ingredients, List<String> procedure) {

        if (recipeID == null) {
            recipeID = UUID.randomUUID().toString();
        }

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

    public String getRecipeID() {
        return recipeID;
    }

    public void setRecipeID(String recipeID) {
        this.recipeID = recipeID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public float getRating() {
        return rating;
    }

    public int getReviewCount() {
        return reviews.size();
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    public List<String> getProcedure() {
        return procedure;
    }

    public void setProcedure(List<String> procedure) {
        this.procedure = procedure;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public String addReview(Review review) {
        reviews.add(review);
        return review.getUsername();
    }

    public Review removeReview(int index) {
        return reviews.remove(index);
    }




}
