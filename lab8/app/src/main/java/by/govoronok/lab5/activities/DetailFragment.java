package by.govoronok.lab5.activities;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

import by.govoronok.lab5.JSONHelper;
import by.govoronok.lab5.R;
import by.govoronok.lab5.model.Item;
import by.govoronok.lab5.repository.DatabaseHelper;

public class DetailFragment extends Fragment {

    Item selectedItem;
    List<Item> itemList;
    private int taskPosition;


    public void setTask(int position) {
        this.taskPosition = position;
    }

    Cursor itemCursor;
    DatabaseHelper sqlHelper;
    SQLiteDatabase db;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sqlHelper = new DatabaseHelper(getContext());
        db = sqlHelper.getWritableDatabase();

        itemCursor =  db.rawQuery("select * from "+ DatabaseHelper.TABLE, null);

        itemList = new ArrayList<Item>();

        itemCursor.moveToFirst();
        while(!itemCursor.isAfterLast()) {
            Item item = new Item();
            item.setId(itemCursor.getInt(itemCursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ID)));
            item.setName(itemCursor.getString(itemCursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_NAME)));
            item.setDescription(itemCursor.getString(itemCursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_DESCRIPTION)));
            item.setReturningPlace(itemCursor.getString(itemCursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_RETURNINGPLACE)));
            item.setFoundDate(itemCursor.getString(itemCursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_FOUNDDATE)));
            item.setPicture(itemCursor.getString(itemCursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_PICTURE)));
            item.setFoundPlace(itemCursor.getString(itemCursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_FOUNDPLACE)));
            itemList.add(item); //add the item
            itemCursor.moveToNext();
        }

    }

    @Override
    public void onStart() {
        super.onStart();
        View view = getView();
        if (view != null) {
            selectedItem = itemList.get(taskPosition);
            TextView textViewName = (TextView)view.findViewById(R.id.textViewName);
            TextView textViewDescription = (TextView)view.findViewById(R.id.textViewDescription);
            TextView textViewFoundDate = (TextView)view.findViewById(R.id.textViewDuration);
            TextView textViewFoundPlace = (TextView)view.findViewById(R.id.textViewDifficulty);
            TextView textViewReturningPlace = (TextView)view.findViewById(R.id.placeHowToReturn);
            textViewName.setText(selectedItem.getName());
            textViewDescription.setText(selectedItem.getDescription());
            textViewFoundDate.setText(selectedItem.getFoundDate());
            textViewFoundPlace.setText(selectedItem.getFoundPlace());
            textViewReturningPlace.setText(selectedItem.getReturningPlace());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail, container, false);
    }
}