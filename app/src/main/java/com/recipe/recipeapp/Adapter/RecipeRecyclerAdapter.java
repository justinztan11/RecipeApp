package com.recipe.recipeapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.recipe.recipeapp.DetailsActivity;
import com.recipe.recipeapp.Objects.Recipe;
import com.recipe.recipeapp.R;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class RecipeRecyclerAdapter extends RecyclerView.Adapter<RecipeRecyclerAdapter.ViewHolder> {
    public static final String ITEM_ID_KEY = "item_id";
    private List<Recipe> recipeList;
    private Context c;

    //provides reference to views for each data object
    public static class ViewHolder extends RecyclerView.ViewHolder {
        //all elements of one list entry
        public TextView name;
        public TextView ratingNumber;
        public RatingBar ratingBar;
        public TextView description;
        public CardView card;
        public ImageView image;

        //gets all elements of entry and stores them in instance vars
        public ViewHolder(View v) {
            super(v);
            name = v.findViewById(R.id.recipe_name);
            description = v.findViewById(R.id.description);
            ratingNumber = v.findViewById(R.id.rating_number);
            ratingBar = v.findViewById(R.id.rating_bar);
            card = v.findViewById(R.id.card_view);
        }
    }

    //stores set of data (for now strings)
    public RecipeRecyclerAdapter(Context context, List<Recipe> recipeList) {
        this.recipeList = recipeList;
        this.c = context;
    }

    /*
     *  Inflates (renders) view elements, method does not need to be updated to change layout of list items
     *
     *  @return ViewHolder viewholder with rendered view
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item, parent, false);
        return new ViewHolder(v);
    }

    /*
     *  Writes data onto views
     */
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.name.setText(recipeList.get(position).getName());
        holder.description.setText(recipeList.get(position).getDescription());
        //holder.ratingNumber.setText(data[position].getReviewCount());
        holder.ratingNumber.setText("(" + Integer.toString(recipeList.get(position).getReviewCount()) + ")");
        holder.ratingBar.setRating(recipeList.get(position).getRating());

        final String recipeID = recipeList.get(position).getRecipeID();

        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent intent = new Intent(c, DetailsActivity.class);
                intent.putExtra(ITEM_ID_KEY, recipeID);
                c.startActivity(intent);
            }
        });
    }

    /*
     *  Gets number of items in adapter
     */
    @Override
    public int getItemCount() {
        return recipeList.size();
    }

    // sorts recipes based on alphabet
    public void sortRecipeAlpha() {
        Collections.sort(recipeList, new Comparator<Recipe>() {
            @Override
            public int compare(Recipe recipe1, Recipe recipe2){
                return recipe1.getName().compareTo(recipe2.getName());
            }
        });
    }

    // sorts recipes based on rating
    public void sortRecipeRating() {
        Collections.sort(recipeList, new Comparator<Recipe>() {
            @Override
            public int compare(Recipe recipe1, Recipe recipe2){
                return (int) (recipe1.getRating() - recipe2.getRating());
            }
        });
    }

    // sorts recipes based on number of reviews (i.e. popularity)
    public void sortRecipeReviews() {
        Collections.sort(recipeList, new Comparator<Recipe>() {
            @Override
            public int compare(Recipe recipe1, Recipe recipe2){
                return recipe1.getReviewCount() - recipe2.getReviewCount();
            }
        });
    }
}
