package by.govoronok.lab5;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ArrayAdapter<Item> adapter;
    private List<Item> items;
    private ListView listView;


    public static final int IDM_OPEN = 101;
    public static final int IDM_EDIT = 102;
    public static final int IDM_DELETE = 103;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        listView = (ListView) findViewById(R.id.itemsList);
        registerForContextMenu(listView);
        Item item = new Item();
        item.setName("car");
        item.setReturningPlace("dsv");
        item.setFoundPlace("sdoibva");
        item.setDescription("sibvsd");
        item.setFoundDate("22/12/2021");
        items = new ArrayList<Item>();
        items.add(item);
        items = JSONHelper.importFromJSON(this);
        adapter = new CustomAdapter(this, R.layout.custom_list_view, items);
        //adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id)
            {
                Item item2 = items.get(position);
                goToItemInfo(item2);
            }
        });
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

                        // Specifying a listener allows you to take an action before dismissing the dialog.
                        // The dialog is automatically dismissed when a dialog button is clicked.
                        .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {


                                Item itemSel = adapter.getItem(info.position);

                                items.removeIf(t -> t.getId().equals(itemSel.getId()));
                                //items.remove(info.position);
                                JSONHelper.exportToJSON(MainActivity.this, items);
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
}