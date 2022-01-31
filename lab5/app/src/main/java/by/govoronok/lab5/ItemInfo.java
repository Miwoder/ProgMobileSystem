package by.govoronok.lab5;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class ItemInfo extends AppCompatActivity {

    Item item;
    TextView name, description, foundPlace, returningPlace, date;
    ImageView photo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_info);

        name = (TextView) findViewById(R.id.nameInfo);
        description = (TextView) findViewById(R.id.descriptionInfo);
        foundPlace = (TextView) findViewById(R.id.foundPlaceInfo);
        returningPlace = (TextView) findViewById(R.id.returningPlaceInfo);
        date = (TextView) findViewById(R.id.foundDateEdit);
        photo = (ImageView) findViewById(R.id.imageViewEdit);

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