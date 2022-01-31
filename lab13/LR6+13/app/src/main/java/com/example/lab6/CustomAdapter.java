package com.example.lab6;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

public class CustomAdapter extends ArrayAdapter<ModelRecipe> {

    List<ModelRecipe> modelRecipeList;
    Context context;
    int resource;

    public CustomAdapter(Context context, int resource, List<ModelRecipe> modelRecipeList) {
        super(context, resource, modelRecipeList);
        this.modelRecipeList = modelRecipeList;
        this .context = context;
        this.resource = resource;
    }

    //this will return the ListView Item as a View
    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        //we need to get the view of the xml for our list item
        //And for this we need a layoutinflater
        LayoutInflater layoutInflater = LayoutInflater.from(context);

        //getting the view
        View view = layoutInflater.inflate(resource, null, false);

        ImageView photo = view.findViewById(R.id.image);
        TextView textViewMeal = view.findViewById(R.id.meal);
        TextView textViewCategory = view.findViewById(R.id.Category);
        TextView textViewIngred = view.findViewById(R.id.Time);
        TextView textViewRecipe = view.findViewById(R.id.Ingredients);
        TextView textViewTime = view.findViewById(R.id.Recipe);
        ModelRecipe modelRecipe = modelRecipeList.get(position);

        textViewMeal.setText("Meal: " +modelRecipe.getMeal());
        textViewCategory.setText("Category: " +modelRecipe.getCategory());
        textViewIngred.setText("Ingredients: " + modelRecipe.getIngredients());
        textViewRecipe.setText("Priority: " + modelRecipe.getRecipe());
        textViewTime.setText("Time: " + modelRecipe.getTime());
        try {
            File file=new File(modelRecipe.getPhoto());
            photo.setImageBitmap(BitmapFactory.decodeStream(new FileInputStream(file)));
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }

        //finally returning the view
        return view;
    }

}
