package com.example.lab6;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.ListFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


public class RecipeListFragment extends ListFragment {

    CustomAdapter adapter;
    List<ModelRecipe> modelRecipeList;

    static interface RecipeListListener {
        void itemClicked(int position);
    };

    private RecipeListListener listener;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.listener = (RecipeListListener)context;
    }
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        if (listener != null) {
            listener.itemClicked(position);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
//        modelRecipeList = new ArrayList<ModelRecipe>();
//        modelRecipeList.add(new ModelRecipe("Potato&Cheese", "Dinner",
//                "Cook it", "30", "potato, cheese"));
//        modelRecipeList.add(new ModelRecipe("Eggs", "Breakfast",
//                "Add eggs", "15", "eggs"));
//        JSONHelper.exportToJSON(inflater.getContext(), modelRecipeList);
            modelRecipeList = JSONHelper.importFromJSON(inflater.getContext());
        adapter = new CustomAdapter(inflater.getContext(), R.layout.custom_listview, modelRecipeList);
        setListAdapter(adapter);
        return super.onCreateView(inflater, container, savedInstanceState);

    }
}