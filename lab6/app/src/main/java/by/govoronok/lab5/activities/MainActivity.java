package by.govoronok.lab5.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.Comparator;
import java.util.List;

import by.govoronok.lab5.CustomAdapter;
import by.govoronok.lab5.model.Item;
import by.govoronok.lab5.JSONHelper;
import by.govoronok.lab5.R;

public class MainActivity extends AppCompatActivity implements ItemListFragment.ItemListListener {

    private ArrayAdapter<Item> adapter;
    private List<Item> items;
    private ListView listView;
    ItemListFragment taskListFragment;


    public static final int IDM_OPEN = 101;
    public static final int IDM_EDIT = 102;
    public static final int IDM_DELETE = 103;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


//        listView = (ListView) findViewById(R.id.itemsList);
//        registerForContextMenu(listView);
        items = JSONHelper.importFromJSON(this);


        taskListFragment = (ItemListFragment) getSupportFragmentManager().findFragmentById(R.id.itemsListF);
        registerForContextMenu(taskListFragment.getListView());


//        adapter = new CustomAdapter(this, R.layout.custom_list_view, items);
//        listView.setAdapter(adapter);
//
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
//            @Override
//            public void onItemClick(AdapterView<?> parent, View v, int position, long id)
//            {
//                Item item2 = items.get(position);
//                goToItemInfo(item2);
//            }
//        });
    }

    public void goToItemInfo(Item item){
        Intent intent = new Intent(this, ItemInfo.class);
        intent.putExtra(Item.class.getSimpleName(), item);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch(id){
            case R.id.create :
                Intent intent = new Intent(this, AddNew.class);
                startActivity(intent);
                return true;
            case R.id.sortById:
                items.sort(Comparator.comparing(task->task.getId()));
                adapter.notifyDataSetChanged();
                return true;
            case R.id.sortByName:
                items.sort(Comparator.comparing(task->task.getName()));
                adapter.notifyDataSetChanged();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(Menu.NONE, IDM_OPEN, Menu.NONE, "Просмотреть");
        menu.add(Menu.NONE, IDM_EDIT, Menu.NONE, "Изменить");
        menu.add(Menu.NONE, IDM_DELETE, Menu.NONE, "Удалить");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        Item selectedItem = items.get(info.position);
        switch (item.getItemId()) {
            case IDM_OPEN:
                Intent inspectIntent = new Intent(getBaseContext(), ItemInfo.class);
                inspectIntent.putExtra(Item.class.getSimpleName(), selectedItem);
                startActivity(inspectIntent);
                break;
            case IDM_EDIT:
                Intent editIntent = new Intent(getBaseContext(), EditActivity.class);
                editIntent.putExtra("id", selectedItem.getId());
                startActivity(editIntent);
                break;
            case IDM_DELETE:
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Удалить задачу")
                        .setMessage("Вы уверены, что хотите удалить выбранную задачу?")
                        .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Item itemSel = adapter.getItem(info.position);
                                items.removeIf(t -> t.getId().equals(itemSel.getId()));
                                JSONHelper.exportToJSON(MainActivity.this, items);

                                adapter = new CustomAdapter(taskListFragment.getContext(), R.layout.custom_list_view, items);
                                taskListFragment.setListAdapter(adapter);

                                adapter.notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("Отмена", null)
                        .show();
                break;
            default:
                return super.onContextItemSelected(item);
        }
        return true;
    }


    @Override
    public void itemClicked(int position) {
        View fragmentContainer = findViewById(R.id.detail_frame);
        if(fragmentContainer!=null){
            DetailFragment fragm = new DetailFragment();
            FragmentTransaction ftr = getSupportFragmentManager().beginTransaction();
            fragm.setTask(position);
            ftr.replace(R.id.detail_frame, fragm);
            ftr.addToBackStack(null);
            ftr.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ftr.commit();
        }
        else{
            Item selectedItem = items.get(position);
            Intent intent = new Intent(this, ItemInfo.class);
            intent.putExtra(Item.class.getSimpleName(), selectedItem);
            startActivity(intent);
        }
    }
}