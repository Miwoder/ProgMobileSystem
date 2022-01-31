package by.govoronok.lab5.activities;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.fragment.app.Fragment;

import java.util.List;

import by.govoronok.lab5.JSONHelper;
import by.govoronok.lab5.R;
import by.govoronok.lab5.model.Item;

public class DetailFragment extends Fragment {

    Item selectedItem;
    List<Item> itemList;
    private int taskPosition;

    public void setTask(int position) {
        this.taskPosition = position;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        itemList = JSONHelper.importFromJSON(getContext());
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