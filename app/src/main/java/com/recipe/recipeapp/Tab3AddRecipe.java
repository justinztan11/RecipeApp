package com.recipe.recipeapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
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

import com.recipe.recipeapp.Objects.Recipe;
import com.recipe.recipeapp.Sample_Data.RecipeData;
import com.recipe.recipeapp.Singleton.CategorySelectedSingleton;

import static android.app.Activity.RESULT_OK;


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
                android.R.layout.simple_spinner_item,
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

        //for selecting camera or gallery

        getImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder myAlertDialog = new AlertDialog.Builder(getActivity());
                myAlertDialog.setTitle("Image Option");
                myAlertDialog.setMessage("Select Picture Mode");

                myAlertDialog.setPositiveButton("Gallery", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface arg0, int arg1)
                    {
                        Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(pickPhoto , 1);
                    }
                });
                myAlertDialog.setNegativeButton("Camera", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface arg0, int arg1)
                    {
                        Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(takePicture, 0);

                    }
                });
                myAlertDialog.show();



            }
        });
        //when the add button is clicked, new recipe is added with the new information

        Button addButton = (Button) rootView.findViewById(R.id.add);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                name=nameInput.getText().toString();
                description=descriptionInput.getText().toString();

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

    @Override
    public void setUserVisibleHint(boolean visible) {
        super.setUserVisibleHint(visible);
        if (visible && isResumed()) {
            onResume();
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        if (!getUserVisibleHint()) {
            return;
        }

        // FLOATING ACTION BUTTON
        // set onclick listener
        MainActivity mainActivity = (MainActivity)getActivity();
        FloatingActionButton fabAdd = mainActivity.fab;

        fabAdd.setImageResource(R.drawable.ic_add);
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Add Ingredient", Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        switch(requestCode) {
            case 0:
                if(resultCode == RESULT_OK){
                    Uri selectedImage = imageReturnedIntent.getData();

                }

                break;
            case 1:
                if(resultCode == RESULT_OK){
                    Uri selectedImage = imageReturnedIntent.getData();
                }
                break;
        }
    }
}

