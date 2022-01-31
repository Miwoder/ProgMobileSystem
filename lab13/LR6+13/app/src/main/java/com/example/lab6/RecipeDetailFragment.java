package com.example.lab6;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;

import java.util.List;

public class RecipeDetailFragment extends Fragment {

    ModelRecipe selectedModelRecipe;
    List<ModelRecipe> modelRecipeList;
    private int taskPosition;

    public void setTask(int position) {
        this.taskPosition = position;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        modelRecipeList = JSONHelper.importFromJSON(getContext());
    }

    @Override
    public void onStart() {
        super.onStart();
        View view = getView();
        if (view != null) {
            selectedModelRecipe = modelRecipeList.get(taskPosition);
            TextView textViewMeal = (TextView)view.findViewById(R.id.textViewMeal);
            TextView textViewCategory = (TextView)view.findViewById(R.id.textViewCategory);
            TextView textViewTime = (TextView)view.findViewById(R.id.textViewTime);
            TextView textViewRecipe = (TextView)view.findViewById(R.id.textViewRecipe);
            TextView textViewIngredients = (TextView)view.findViewById(R.id.textViewIngredients);


            textViewMeal.setText(selectedModelRecipe.getMeal());
            textViewCategory.setText(selectedModelRecipe.getCategory());
            textViewTime.setText(selectedModelRecipe.getTime());
            textViewRecipe.setText(selectedModelRecipe.getRecipe());
            textViewIngredients.setText(selectedModelRecipe.getIngredients());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_task_detail, container, false);
    }
}