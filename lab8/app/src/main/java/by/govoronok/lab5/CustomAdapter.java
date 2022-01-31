package by.govoronok.lab5;


import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

import by.govoronok.lab5.model.Item;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

    private final LayoutInflater inflater;
    private final List<Item> items;

    public static final int IDM_OPEN = 101;
    public static final int IDM_EDIT = 102;
    public static final int IDM_DELETE = 103;


    public CustomAdapter(Context context, List<Item> items) {
        this.items = items;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public CustomAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.custom_list_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CustomAdapter.ViewHolder holder, int position) {
        Item item = items.get(position);
        holder.nameView.setText("Name: " + item.getName());
        holder.descriptionView.setText("Description: " + item.getDescription());
        try {
            File file=new File(item.getPicture());
            holder.imageView.setImageBitmap(BitmapFactory.decodeStream(new FileInputStream(file)));
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {

        final ImageView imageView;
        final TextView nameView, descriptionView;
        CardView cardView;
        ViewHolder(View view){
            super(view);
            nameView = view.findViewById(R.id.nameLV);
            descriptionView = view.findViewById(R.id.descriptionLV);
            imageView = view.findViewById(R.id.imageLV);

            cardView = view.findViewById(R.id.cardView);
            cardView.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v,
                                        ContextMenu.ContextMenuInfo menuInfo) {
            menu.add(Menu.NONE, IDM_OPEN, Menu.NONE, "Просмотреть");
            menu.add(Menu.NONE, IDM_EDIT, Menu.NONE, "Изменить");
            menu.add(Menu.NONE, IDM_DELETE, Menu.NONE, "Удалить");
        }
    }

}

//
//    List<Item> items;
//    Context context;
//    int resource;
//
//    public CustomAdapter(Context context, int resource, List<Item> taskList) {
//        super(context, resource, taskList);
//        this.items = taskList;
//        this.context = context;
//        this.resource = resource;
//    }
//
//    //this will return the ListView Item as a View
//    @NonNull
//    @Override
//    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
//
//        //we need to get the view of the xml for our list item
//        //And for this we need a layoutinflater
//        LayoutInflater layoutInflater = LayoutInflater.from(context);
//
//        //getting the view
//        View view = layoutInflater.inflate(resource, null, false);
//
//        ImageView imageView = view.findViewById(R.id.imageLV);
//        TextView textViewName = view.findViewById(R.id.nameLV);
//        TextView textViewDescription = view.findViewById(R.id.descriptionLV);
//
//        Item item = items.get(position);
//
//        textViewName.setText(item.getName());
//        textViewDescription.setText(item.getDescription());
//
//        try {
//            File file=new File(item.getPicture());
//            imageView.setImageBitmap(BitmapFactory.decodeStream(new FileInputStream(file)));
//        }
//        catch (FileNotFoundException e)
//        {
//            e.printStackTrace();
//        }
//
//        //finally returning the view
//        return view;
//    }
//
//}