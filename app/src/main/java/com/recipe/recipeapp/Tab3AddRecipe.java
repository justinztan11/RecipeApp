package com.recipe.recipeapp;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.recipe.recipeapp.Adapter.RecipeListAdapter;
import com.recipe.recipeapp.Adapter.RecipeRecyclerAdapter;
import com.recipe.recipeapp.Objects.Recipe;
import com.recipe.recipeapp.Sample_Data.RecipeData;
import com.recipe.recipeapp.Singleton.CategorySelectedSingleton;




public class Tab3AddRecipe extends Fragment {

    private String name,description,category;
    private float rating;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab3_add_recipe, container, false);
        super.onViewCreated(rootView, savedInstanceState);

        final EditText nameInput = (EditText) rootView.findViewById(R.id.editName);
        final EditText descriptionInput = (EditText) rootView.findViewById(R.id.editDescription);
        final Button getImage = (Button) rootView.findViewById(R.id.button2);
        final RatingBar ratBar = (RatingBar) rootView.findViewById(R.id.ratingBar);


        ratBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                rating=v;
            }
        });

        final Spinner spinner = (Spinner) rootView.findViewById(R.id.spinner2);
        ArrayAdapter<String> categoryListAdapter = new ArrayAdapter<>(this.getActivity(),
                R.layout.custom_spinner_item,
                getResources().getStringArray(R.array.categories_list));

        categoryListAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(categoryListAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                CategorySelectedSingleton.getInstance().categorySelected =
                        category=spinner.getSelectedItem().toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                CategorySelectedSingleton.getInstance().categorySelected = "All";
            }
        });

        name=nameInput.getText().toString();
        description=descriptionInput.getText().toString();


        //when the add button is clicked, new recipe is added with the new information

        Button addButton = (Button) rootView.findViewById(R.id.add);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Recipe newRecipe = (new Recipe(null, name,description,
                        null, rating, category, null, null));
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




