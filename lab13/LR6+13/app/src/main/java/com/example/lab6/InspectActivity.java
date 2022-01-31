package com.example.lab6;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class InspectActivity extends AppCompatActivity {

    TextView textViewMeal, textViewCategory, textViewIngredients, textViewRecipe, textViewTime;
    ImageView imageView;
    ModelRecipe selectedModelRecipe;

    private void initializeWidgets() {
        textViewMeal = (TextView) findViewById(R.id.textViewMeal);
        textViewCategory = (TextView) findViewById(R.id.textViewCategory);
        textViewIngredients = (TextView) findViewById(R.id.textViewIngredients);
        textViewRecipe = (TextView) findViewById(R.id.textViewRecipe);
        textViewTime = (TextView) findViewById(R.id.textViewTime);
        imageView = (ImageView) findViewById(R.id.imageCreate);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inspect);
        initializeWidgets();
        Bundle arguments = getIntent().getExtras();
        selectedModelRecipe = (ModelRecipe) arguments.getSerializable(ModelRecipe.class.getSimpleName());
        textViewMeal.setText(selectedModelRecipe.getMeal());
        textViewCategory.setText(selectedModelRecipe.getCategory());
        textViewIngredients.setText(selectedModelRecipe.getIngredients());
        textViewRecipe.setText(selectedModelRecipe.getRecipe());
        textViewTime.setText(selectedModelRecipe.getTime());
        try {
            File file=new File(selectedModelRecipe.getPhoto());
            imageView.setImageBitmap(BitmapFactory.decodeStream(new FileInputStream(file)));
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
    }
}