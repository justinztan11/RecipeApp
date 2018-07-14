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

        nameInput = (EditText) getView().findViewById(R.id.editName);
        descriptionInput = (EditText) getView().findViewById(R.id.editDescription);
        addButton = (Button) getView().findViewById(R.id.add);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name=nameInput.getText().toString();
                description=descriptionInput.getText().toString();
            }
        });




        return rootView;

    }
}




