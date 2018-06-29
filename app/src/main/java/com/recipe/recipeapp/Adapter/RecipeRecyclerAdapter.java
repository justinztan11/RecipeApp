package com.recipe.recipeapp.Adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.recipe.recipeapp.Objects.Recipe;
import com.recipe.recipeapp.R;

public class RecipeRecyclerAdapter extends RecyclerView.Adapter<RecipeRecyclerAdapter.ViewHolder> {
    private Recipe[] data;

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
            name = v.findViewById(R.id.recipeName);
            description = v.findViewById(R.id.description);
            ratingNumber = v.findViewById(R.id.ratingNumber);
            ratingBar = v.findViewById(R.id.ratingBar);
            card = v.findViewById(R.id.card_view);
        }
    }

    //stores set of data (for now strings)
    public RecipeRecyclerAdapter(Recipe[] data) {
        this.data = data;
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
        holder.name.setText(data[position].getName());
        holder.description.setText(data[position].getDescription());
        //holder.ratingNumber.setText(data[position].getReviewCount());
        holder.ratingNumber.setText("(" + Integer.toString(data[position].getReviewCount()) + ")");
        holder.ratingBar.setRating(data[position].getRating());
    }

    /*
     *  Gets number of items in adapter
     */
    @Override
    public int getItemCount() {
        return data.length;
    }
}
