package com.recipe.recipeapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.recipe.recipeapp.Adapter.RecipeRecyclerAdapter;

public class DetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        String recipeID = getIntent().getExtras().getString("recipeID");
        String name = getIntent().getExtras().getString("name");
        String description = getIntent().getExtras().getString("description");
        String ratingNumber = getIntent().getExtras().getString("ratingNumber");

        TextView nameView = (TextView)findViewById(R.id.details_name);
        nameView.setText(name);

        TextView descriptionView = (TextView)findViewById(R.id.details_description);
        nameView.setText(description);

        TextView ratingNumberView = (TextView)findViewById(R.id.details_rating_number);
        nameView.setText(ratingNumber);
    }
}
