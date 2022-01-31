package by.govoronok.lab5.activities;

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
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import by.govoronok.lab5.model.Item;
import by.govoronok.lab5.JSONHelper;
import by.govoronok.lab5.R;

public class EditActivity extends AppCompatActivity {

    private ArrayAdapter<Item> adapter;
    private List<Item> items;
    Item item;
    Item itemNew = new Item();
    TextInputLayout nameText, descriptionText, foundPlaceText, returningPlaceText;
    EditText dateText;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        items = new ArrayList<Item>();

        nameText = (TextInputLayout) findViewById(R.id.nameInfo);
        descriptionText = (TextInputLayout) findViewById(R.id.descriptionInfo);
        foundPlaceText = (TextInputLayout) findViewById(R.id.foundPlaceInfo);
        returningPlaceText = (TextInputLayout) findViewById(R.id.returningPlace);
        dateText = (EditText) findViewById(R.id.foundDateEdit);
        imageView = (ImageView) findViewById(R.id.imageViewEdit);

        Bundle arguments = getIntent().getExtras();
        int id = arguments.getInt("id");
        items = JSONHelper.importFromJSON(getBaseContext());
        item = items.stream().filter(x->x.getId().equals(id)).findFirst().orElse(null);

        nameText.getEditText().setText(item.getName());
        descriptionText.getEditText().setText(item.getDescription());
        foundPlaceText.getEditText().setText(item.getFoundPlace());
        returningPlaceText.getEditText().setText(item.getReturningPlace());
        dateText.setText(item.getFoundDate());

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
                        Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
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

    public void editItem(View view) throws ParseException {
        String name = nameText.getEditText().getText().toString();
        String description = descriptionText.getEditText().getText().toString();
        String foundPlace = foundPlaceText.getEditText().getText().toString();
        String returningPlace = returningPlaceText.getEditText().getText().toString();

//        SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
//
//        String dateInString = dateText.getText().toString();
//        Date foundDate = formatter.parse(dateInString);
        String foundDate = dateText.getText().toString();

        itemNew.setDescription(description);
        itemNew.setName(name);
        itemNew.setFoundDate(foundDate);
        itemNew.setFoundPlace(foundPlace);
        itemNew.setReturningPlace(returningPlace);

        items = JSONHelper.importFromJSON(this);
        items.removeIf(t -> t.getId().equals(item.getId()));
        items.add(itemNew);
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