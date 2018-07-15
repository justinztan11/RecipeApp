package com.recipe.recipeapp;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.recipe.recipeapp.Adapter.RecipeListAdapter;
import com.recipe.recipeapp.Adapter.RecipeRecyclerAdapter;
import com.recipe.recipeapp.Objects.Recipe;
import com.recipe.recipeapp.Sample_Data.RecipeData;


public class Tab3AddRecipe extends Fragment {

    String name,description;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab3_add_recipe, container, false);
        super.onViewCreated(rootView, savedInstanceState);

        final EditText nameInput = (EditText) rootView.findViewById(R.id.editName);
        final EditText descriptionInput = (EditText) rootView.findViewById(R.id.editDescription);
        Button addButton = (Button) rootView.findViewById(R.id.add);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name=nameInput.getText().toString();
                description=descriptionInput.getText().toString();

                Recipe newRecipe = (new Recipe(null, name,description,
                        null, 2.5f, "noodle", null, null));
                RecipeData recipeData = new RecipeData();
                recipeData.addRecipe(newRecipe);

                Context context = getActivity();
                String text = "New Recipe added!";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();


            }
        });



        return rootView;

    }
}




