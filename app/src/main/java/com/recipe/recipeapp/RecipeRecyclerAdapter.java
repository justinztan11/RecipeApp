package com.recipe.recipeapp;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class RecipeRecyclerAdapter extends RecyclerView.Adapter<RecipeRecyclerAdapter.ViewHolder> {
    private String[] data;

    //provides reference to views for each data object
    public static class ViewHolder extends RecyclerView.ViewHolder {
        //all elements of one list entry
        public TextView text;
        public CardView card;

        //gets all elements of entry and stores them in instance vars
        public ViewHolder(View v) {
            super(v);
            text = v.findViewById(R.id.card_text);
            card = v.findViewById(R.id.card_view);
        }
    }

    //stores set of data (for now strings)
    public RecipeRecyclerAdapter(String[] data) {
        this.data = data;
    }

    /*
     *  Inflates (renders) view elements, method does not need to be updated to change layout of list items
     *
     *  @return ViewHolder viewholder with rendered view
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_card, parent, false);
        return new ViewHolder(v);
    }

    /*
     *  Writes data onto views
     */
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.text.setText(data[position]);
    }

    /*
     *  Gets number of items in adapter
     */
    @Override
    public int getItemCount() {
        return data.length;
    }
}
