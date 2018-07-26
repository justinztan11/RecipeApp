package com.recipe.recipeapp.Singleton;

import java.util.ArrayList;
import java.util.List;

public class IngredientsSelectedSingleton {

    public List<String> ingredientList = new ArrayList<>();

    private static final IngredientsSelectedSingleton ourInstance = new IngredientsSelectedSingleton();

    public static IngredientsSelectedSingleton getInstance() {
        return ourInstance;
    }

}
