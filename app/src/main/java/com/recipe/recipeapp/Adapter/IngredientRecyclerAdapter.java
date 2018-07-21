package com.recipe.recipeapp.Adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.recipe.recipeapp.R;
import com.recipe.recipeapp.Tab2IngredientSearch;

import java.util.List;

public class IngredientRecyclerAdapter extends RecyclerView.Adapter<IngredientRecyclerAdapter.ViewHolder>{

    private List<String> ingredientList;
    private Context c;

    //provides reference to views for each data object
    public static class ViewHolder extends RecyclerView.ViewHolder {

        //all elements of one list entry
        public TextView text;
        public CardView card;

        //gets all elements of entry and stores them in instance vars

        public ViewHolder(View v) {
            super(v);
            text = v.findViewById(R.id.ingredient_name);
            card = v.findViewById(R.id.ingredient_card_view);
        }
    }

    //stores set of data in strings
    public IngredientRecyclerAdapter(Context context, List<String> ingredientList) {
        this.ingredientList = ingredientList;
        this.c = context;
    }

    /*
     *  Inflates (renders) view elements, method does not need to be updated to change layout of list items
     *
     *  @return ViewHolder viewholder with rendered view
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.ingredient_card_item, parent, false);
        return new ViewHolder(v);
    }

    /*
     *  Writes data onto views
     */
    @Override
    public void onBindViewHolder(IngredientRecyclerAdapter.ViewHolder holder, int position) {

        holder.text.setText(ingredientList.get(position));
    }

    /*
     *  Gets number of items in adapter
     */
    @Override
    public int getItemCount() {
        return ingredientList.size();
    }

    /*
     *  Sets ingredient list with given list
     */
    public boolean addIngredient(String ingredient) {
        if (!ingredientList.contains(ingredient)) {
            ingredientList.add(ingredient);
            return true;
        } else {
            return false;
        }
    }
}
