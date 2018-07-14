package com.recipe.recipeapp;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


import com.recipe.recipeapp.Objects.Recipe;


public class Tab3AddRecipe extends Fragment {

    EditText nameInput;
    EditText descriptionInput;
    Button  addButton;
    String name,description;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab3_add_recipe, container, false);
        super.onViewCreated(rootView, savedInstanceState);

        return rootView;

    }
}




