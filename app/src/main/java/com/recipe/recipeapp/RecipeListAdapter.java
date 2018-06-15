package com.recipe.recipeapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class RecipeListAdapter extends ArrayAdapter<Recipe>{

    private List<Recipe> recipeList;
    /**
     * Constructor
     *
     * @param context  The current context.
     * @param resource The resource ID for a layout file containing a TextView to use when
     *                 instantiating views.
     * @param objects  The objects to represent in the ListView.
     */
    public RecipeListAdapter(@NonNull Context context, int resource, List<Recipe> objects) {
        super(context, resource, objects);
        recipeList = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.recipe_item, parent, false);
        }

        Recipe recipe = recipeList.get(position);
        TextView recipeName = convertView.findViewById(R.id.recipeName);
        recipeName.setText(recipe.getName());

        return convertView;
    }
}
