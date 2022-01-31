package by.govoronok.lab5;


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

import by.govoronok.lab5.model.Item;

public class CustomAdapter extends ArrayAdapter<Item> {

    List<Item> items;
    Context context;
    int resource;

    public CustomAdapter(Context context, int resource, List<Item> taskList) {
        super(context, resource, taskList);
        this.items = taskList;
        this.context = context;
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

        ImageView imageView = view.findViewById(R.id.imageLV);
        TextView textViewName = view.findViewById(R.id.nameLV);
        TextView textViewDescription = view.findViewById(R.id.descriptionLV);

        Item item = items.get(position);

        textViewName.setText(item.getName());
        textViewDescription.setText(item.getDescription());

        try {
            File file=new File(item.getPicture());
            imageView.setImageBitmap(BitmapFactory.decodeStream(new FileInputStream(file)));
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }

        //finally returning the view
        return view;
    }

}