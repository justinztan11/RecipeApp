package com.recipe.recipeapp;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.recipe.recipeapp.Objects.Recipe;

public class Tab3AddRecipe extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab3_add_recipe, container, false);
        return rootView;
    }

    TextInputEditText editTextName =(TextInputEditText)findViewById(R.id.editName);
    editTextName.



}
