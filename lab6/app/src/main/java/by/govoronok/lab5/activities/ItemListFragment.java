package by.govoronok.lab5.activities;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.ListFragment;

import java.util.List;

import by.govoronok.lab5.CustomAdapter;
import by.govoronok.lab5.JSONHelper;
import by.govoronok.lab5.R;
import by.govoronok.lab5.model.Item;

public class ItemListFragment extends ListFragment {

    CustomAdapter adapter;
    List<Item> itemList;

    static interface ItemListListener {
        void itemClicked(int position);
    };
    private ItemListListener listener;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.listener = (ItemListListener)context;
    }
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        if (listener != null) {
            listener.itemClicked(position);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        itemList = JSONHelper.importFromJSON(inflater.getContext());
        adapter = new CustomAdapter(inflater.getContext(), R.layout.custom_list_view, itemList);
        setListAdapter(adapter);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

}