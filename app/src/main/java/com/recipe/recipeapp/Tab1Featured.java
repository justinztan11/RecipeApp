package com.recipe.recipeapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class Tab1Featured extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab1_featured, container, false);

        return rootView;
    }
//
//    @Override
//    public void setUserVisibleHint(boolean visible) {
//        super.setUserVisibleHint(visible);
//        if (visible && isResumed()) {
//            onResume();
//        }
//    }
//
//
//    @Override
//    public void onResume() {
//        super.onResume();
//
//        if (!getUserVisibleHint()) {
//            return;
//        }
//
//        // disable FlOATING ACTION BUTTON
//        MainActivity mainActivity = (MainActivity)getActivity();
//        FloatingActionButton fab = mainActivity.fab;
//        fab.setVisibility(View.GONE);
//    }
}
