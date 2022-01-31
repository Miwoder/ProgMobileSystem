package com.example.lab6;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Spinner;

import com.google.android.material.textfield.TextInputLayout;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

public class EditActivity extends AppCompatActivity {

    ImageView image;
    ModelRecipe selectedModelRecipe;
    ModelRecipe editedModelRecipe = new ModelRecipe();
    TextInputLayout editMeal, editCategory, editIngredients, editRecipe, editTime;
    Button buttonEdit;
    List<ModelRecipe> modelRecipeList;

    private void initializeWidgets() {
        editMeal = (TextInputLayout) findViewById(R.id.textInputLayoutMeal);
        editIngredients = (TextInputLayout) findViewById(R.id.textInputLayoutIngredients);
        editCategory = (TextInputLayout) findViewById(R.id.textInputLayoutCategory);
        editRecipe = (TextInputLayout) findViewById(R.id.textInputLayoutRecipe);
        editTime = (TextInputLayout) findViewById(R.id.textInputLayout);
        buttonEdit = (Button) findViewById(R.id.buttonEdit);
        image = (ImageView) findViewById(R.id.imageEdit);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        Bundle arguments = getIntent().getExtras();
        initializeWidgets();
        modelRecipeList = JSONHelper.importFromJSON(this);
        ModelRecipe modrec = modelRecipeList.get(Integer.parseInt(arguments.get("pos").toString()));
        editMeal.getEditText().setText(modrec.getMeal());
        editIngredients.getEditText().setText(modrec.getCategory());
        editCategory.getEditText().setText(modrec.getRecipe());
        editRecipe.getEditText().setText(modrec.getIngredients());
        editTime.getEditText().setText(modrec.getTime());
        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(EditActivity.this)
                        .setTitle("Save changes")
                        .setMessage("Are you sure?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
//                                editedModelRecipe.setMeal(editMeal.getEditText().getText().toString());
//                                editedModelRecipe.setCategory(editCategory.getEditText().getText().toString());
//                                editedModelRecipe.setRecipe(editRecipe.getEditText().getText().toString());
//                                editedModelRecipe.setTime(editTime.getEditText().getText().toString());
//                                editedModelRecipe.setIngredients(editIngredients.getEditText().getText().toString());
//                                modelRecipeList.remove(modrec);
//                                modelRecipeList.add(editedModelRecipe);
                                ModelRecipe mr =new ModelRecipe(editMeal.getEditText().getText().toString(), editCategory.getEditText().getText().toString(), editRecipe.getEditText().getText().toString(),editTime.getEditText().getText().toString(), editIngredients.getEditText().getText().toString());
                                modelRecipeList.set(Integer.parseInt(arguments.get("pos").toString()), mr);
                                JSONHelper.exportToJSON(EditActivity.this,modelRecipeList);
                                Intent intent = new Intent(EditActivity.this, MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .show();
            }
        });
        ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == RESULT_OK && null != result.getData()) {
                            Uri selectedImage = result.getData().getData();
                            editedModelRecipe.setPhoto(Helper.getRealPathFromURI(getBaseContext(),selectedImage));
                            image.setImageURI(selectedImage);
                        }
                    }
                });
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(
                        Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                someActivityResultLauncher.launch(intent);
            }
        });
    }
}