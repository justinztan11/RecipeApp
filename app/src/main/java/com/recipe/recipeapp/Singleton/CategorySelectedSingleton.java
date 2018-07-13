package com.recipe.recipeapp.Singleton;

public class CategorySelectedSingleton {

    public String categorySelected = "All";

    private static final CategorySelectedSingleton ourInstance = new CategorySelectedSingleton();

    public static CategorySelectedSingleton getInstance() {
        return ourInstance;
    }

}
