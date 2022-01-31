package by.govoronok.lab5.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Configuration;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import by.govoronok.lab5.model.Item;
import by.govoronok.lab5.R;

public class ItemInfo extends AppCompatActivity {

    Item item;
    TextView name, description, foundPlace, returningPlace, date;
    ImageView photo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_info);

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            finish();
            return;
        }

        name = (TextView) findViewById(R.id.nameInfo);
        description = (TextView) findViewById(R.id.descriptionInfo);
        foundPlace = (TextView) findViewById(R.id.foundPlaceInfo);
        returningPlace = (TextView) findViewById(R.id.returningPlaceInfo);
        date = (TextView) findViewById(R.id.foundDateInfo);
        photo = (ImageView) findViewById(R.id.imageViewInfo);

    }

    @Override
    protected void onResume() {
        super.onResume();

            Bundle arguments = getIntent().getExtras();
            if(arguments!=null){
                item = (Item) arguments.getSerializable(Item.class.getSimpleName());
                name.setText(item.getName());
                description.setText(item.getDescription());
                foundPlace.setText(item.getFoundPlace());
                returningPlace.setText(item.getReturningPlace());
                date.setText(item.getFoundDate());
            }

            try {
                File file=new File(item.getPicture());
                photo.setImageBitmap(BitmapFactory.decodeStream(new FileInputStream(file)));
            }
            catch (FileNotFoundException e)
            {
                e.printStackTrace();
            }


    }
}