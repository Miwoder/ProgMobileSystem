package com.example.lab6;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

public class CreateActivity extends AppCompatActivity {

    ImageView imageCreate;
    TextInputLayout createMeal, createCategory, createIngredients, createRecipe, createTime;
    Button buttonCreate;
    List<ModelRecipe> modelRecipeList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        initializeWidgets();
        ModelRecipe newModelRecipe = new ModelRecipe();
        modelRecipeList = JSONHelper.importFromJSON(getBaseContext());
        ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == RESULT_OK && null != result.getData()) {
                            Uri selectedImage = result.getData().getData();
                            newModelRecipe.setPhoto(Helper.getRealPathFromURI(getBaseContext(),selectedImage));
                            imageCreate.setImageURI(selectedImage);
                        }
                    }
                });



        imageCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(
                        Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                someActivityResultLauncher.launch(intent);
            }
        });
        buttonCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newModelRecipe.setMeal(createMeal.getEditText().getText().toString());
                newModelRecipe.setCategory(createCategory.getEditText().getText().toString());
                newModelRecipe.setIngredients(createIngredients.getEditText().getText().toString());
                newModelRecipe.setRecipe(createRecipe.getEditText().getText().toString());
                newModelRecipe.setTime(createTime.getEditText().getText().toString());
                modelRecipeList.add(newModelRecipe);
                JSONHelper.exportToJSON(getBaseContext(), modelRecipeList);
                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }
    private void initializeWidgets() {
        createMeal = (TextInputLayout) findViewById(R.id.textInputLayoutMeal);
        createIngredients = (TextInputLayout) findViewById(R.id.textInputLayoutIngredients);
        createCategory = (TextInputLayout) findViewById(R.id.textInputLayoutCategory);
        createRecipe = (TextInputLayout) findViewById(R.id.textInputLayoutRecipe);
        createTime = (TextInputLayout) findViewById(R.id.textInputLayout);
        buttonCreate = (Button) findViewById(R.id.buttonCreate);
        imageCreate = (ImageView) findViewById(R.id.imageCreate);

    }
}