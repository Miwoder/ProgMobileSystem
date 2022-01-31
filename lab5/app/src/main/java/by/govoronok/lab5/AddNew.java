package by.govoronok.lab5;

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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class AddNew extends AppCompatActivity {

    TextInputLayout nameText, descriptionText, foundPlaceText, returningPlaceText;
    EditText dateText;
    ImageView imageView;
    Item item = new Item();
    private List<Item> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new);

        nameText = (TextInputLayout) findViewById(R.id.nameInfo);
        descriptionText = (TextInputLayout) findViewById(R.id.descriptionInfo);
        foundPlaceText = (TextInputLayout) findViewById(R.id.foundPlaceInfo);
        returningPlaceText = (TextInputLayout) findViewById(R.id.returningPlace);
        dateText = (EditText) findViewById(R.id.foundDateEdit);
        imageView = (ImageView) findViewById(R.id.imageViewEdit);

        ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == RESULT_OK && null != result.getData()) {
                            Uri selectedImage = result.getData().getData();
                            item.setPicture(getRealPathFromURI(selectedImage));
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
        String [] proj={MediaStore.Images.Media.DATA};
        CursorLoader loader = new CursorLoader(this.getBaseContext(), contentUri, proj, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(column_index);
        cursor.close();
        return result;
    }

    public void createItem(View view) throws ParseException {
        String name = nameText.getEditText().getText().toString();
        String description = descriptionText.getEditText().getText().toString();
        String foundPlace = foundPlaceText.getEditText().getText().toString();
        String returningPlace = returningPlaceText.getEditText().getText().toString();

//        SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
//
//        String dateInString = dateText.getText().toString();
//        Date foundDate = formatter.parse(dateInString);
        String foundDate = dateText.getText().toString();

        item.setDescription(description);
        item.setName(name);
        item.setFoundDate(foundDate);
        item.setFoundPlace(foundPlace);
        item.setReturningPlace(returningPlace);

        items = new ArrayList<Item>();
        items = JSONHelper.importFromJSON(this);
        items.add(item);
        boolean result = JSONHelper.exportToJSON(this, items);
        if(result){
            Toast.makeText(this, "Данные сохранены", Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(this, "Не удалось сохранить данные", Toast.LENGTH_LONG).show();
        }

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}