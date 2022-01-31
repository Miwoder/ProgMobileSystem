package by.govoronok.thirdapp.activities;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.CursorLoader;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;

import com.google.android.material.textfield.TextInputLayout;

import by.govoronok.thirdapp.R;
import by.govoronok.thirdapp.model.User;

public class ThirdActivity extends AppCompatActivity {

    User user;
    TextInputLayout socialText;
    ImageView imageView;
    final static String userVariableKey = "USER_VARIABLE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        socialText = (TextInputLayout) findViewById(R.id.socialTextInput);
        imageView = (ImageView) findViewById(R.id.imageView);

        Bundle arguments = getIntent().getExtras();
        if(arguments!=null){
            user = (User) arguments.getSerializable(User.class.getSimpleName());
        }




        ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == RESULT_OK && null != result.getData()) {
                            Uri selectedImage = result.getData().getData();
                            user.setPicture(getRealPathFromURI(selectedImage));
                            imageView.setImageURI(selectedImage);
                        }
                    }
                });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(
                        Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                someActivityResultLauncher.launch(intent);
            }
        });
    }

    public String getRealPathFromURI(Uri contentUri) {
// can post image
        String [] proj={MediaStore.Images.Media.DATA};
        CursorLoader loader = new CursorLoader(this.getBaseContext(), contentUri, proj, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(column_index);
        cursor.close();
        return result;
    }


    public void nextFromThird(View view) {

        String social = socialText.getEditText().getText().toString();
        user.setSocial(social);

        Intent intent = new Intent(this, ConfirmActivity.class);
        intent.putExtra(User.class.getSimpleName(), user);
        startActivity(intent);
    }

    public void backFromThird(View view) {
        Intent intent = new Intent(this, SecondActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        intent.putExtra(User.class.getSimpleName(), user);
        startActivity(intent);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putSerializable(userVariableKey, user);
        super.onSaveInstanceState(outState);

        outState.putString("country", socialText.getEditText().getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        user = (User)savedInstanceState.getSerializable(userVariableKey);

        socialText.getEditText().setText(savedInstanceState.getString("country"));
    }

}