package com.recipe.recipeapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.recipe.recipeapp.Adapter.RecipeRecyclerAdapter;

public class DetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        String recipeID = getIntent().getExtras().getString(RecipeRecyclerAdapter.ITEM_ID_KEY);
        Toast.makeText(this, "Recieved id =: " + recipeID, Toast.LENGTH_SHORT).show();
    }
}
