package by.govoronok.lab5.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.FragmentTransaction;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import by.govoronok.lab5.CustomAdapter;
import by.govoronok.lab5.model.Item;
import by.govoronok.lab5.JSONHelper;
import by.govoronok.lab5.R;
import by.govoronok.lab5.repository.DatabaseHelper;

public class MainActivity extends AppCompatActivity implements ItemListFragment.ItemListListener {

    private ArrayAdapter<Item> adapter;
    private List<Item> items;
    private ListView listView;
    ItemListFragment taskListFragment;

    DatabaseHelper databaseHelper;
    SQLiteDatabase db;
    Cursor itemCursor;
    ListView itemList;
    SimpleCursorAdapter itemAdapter;


    public static final int IDM_OPEN = 101;
    public static final int IDM_EDIT = 102;
    public static final int IDM_DELETE = 103;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseHelper = new DatabaseHelper(getApplicationContext());
        itemList = findViewById(R.id.list);

        //items = JSONHelper.importFromJSON(this);

//        taskListFragment = (ItemListFragment) getSupportFragmentManager().findFragmentById(R.id.itemsListF);
        registerForContextMenu(itemList);

        itemList.setOnItemClickListener(new AdapterView.OnItemClickListener(){
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
    public void onResume() {
        super.onResume();
        // открываем подключение
        db = databaseHelper.getReadableDatabase();

        //получаем данные из бд в виде курсора
        itemCursor =  db.rawQuery("select * from "+ DatabaseHelper.TABLE, null);

        items = new ArrayList<Item>();

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
            items.add(item); //add the item
            itemCursor.moveToNext();
        }

        // определяем, какие столбцы из курсора будут выводиться в ListView
        String[] headers = new String[] {DatabaseHelper.COLUMN_NAME, DatabaseHelper.COLUMN_DESCRIPTION};
        // создаем адаптер, передаем в него курсор
        itemAdapter = new SimpleCursorAdapter(this, android.R.layout.two_line_list_item,
                itemCursor, headers, new int[]{android.R.id.text1, android.R.id.text2}, 0);
        itemList.setAdapter(itemAdapter);
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        // Закрываем подключение и курсор
        db.close();
        itemCursor.close();
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
                itemCursor = db.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE + " ORDER BY " + DatabaseHelper.COLUMN_ID, null);

                itemCursor.moveToFirst();
                while(!itemCursor.isAfterLast()) {
                    Item item2 = new Item();
                    item2.setId(itemCursor.getInt(itemCursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ID)));
                    item2.setName(itemCursor.getString(itemCursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_NAME)));
                    item2.setDescription(itemCursor.getString(itemCursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_DESCRIPTION)));
                    item2.setReturningPlace(itemCursor.getString(itemCursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_RETURNINGPLACE)));
                    item2.setFoundDate(itemCursor.getString(itemCursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_FOUNDDATE)));
                    item2.setPicture(itemCursor.getString(itemCursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_PICTURE)));
                    item2.setFoundPlace(itemCursor.getString(itemCursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_FOUNDPLACE)));
                    items.add(item2); //add the item
                    itemCursor.moveToNext();
                }

                String[] headers = new String[] {DatabaseHelper.COLUMN_NAME, DatabaseHelper.COLUMN_DESCRIPTION};
                itemAdapter = new SimpleCursorAdapter(this, android.R.layout.two_line_list_item,
                        itemCursor, headers, new int[]{android.R.id.text1, android.R.id.text2}, 0);
                itemList.setAdapter(itemAdapter);
                itemAdapter.notifyDataSetChanged();
                return true;

            case R.id.sortByName:
                itemCursor = db.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE + " ORDER BY " + DatabaseHelper.COLUMN_NAME, null);

                itemCursor.moveToFirst();
                while(!itemCursor.isAfterLast()) {
                    Item item2 = new Item();
                    item2.setId(itemCursor.getInt(itemCursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ID)));
                    item2.setName(itemCursor.getString(itemCursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_NAME)));
                    item2.setDescription(itemCursor.getString(itemCursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_DESCRIPTION)));
                    item2.setReturningPlace(itemCursor.getString(itemCursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_RETURNINGPLACE)));
                    item2.setFoundDate(itemCursor.getString(itemCursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_FOUNDDATE)));
                    item2.setPicture(itemCursor.getString(itemCursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_PICTURE)));
                    item2.setFoundPlace(itemCursor.getString(itemCursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_FOUNDPLACE)));
                    items.add(item2); //add the item
                    itemCursor.moveToNext();
                }

                String[] headers2 = new String[] {DatabaseHelper.COLUMN_NAME, DatabaseHelper.COLUMN_DESCRIPTION};
                itemAdapter = new SimpleCursorAdapter(this, android.R.layout.two_line_list_item,
                        itemCursor, headers2, new int[]{android.R.id.text1, android.R.id.text2}, 0);
                itemList.setAdapter(itemAdapter);
                itemAdapter.notifyDataSetChanged();
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
                                db.delete(DatabaseHelper.TABLE, DatabaseHelper.COLUMN_ID + "=" + items.get(item.getGroupId()).getId(), null);
                                itemCursor = db.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE, null);
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
                                    items.add(item); //add the item
                                    itemCursor.moveToNext();
                                }

                                String[] headers = new String[] {DatabaseHelper.COLUMN_NAME, DatabaseHelper.COLUMN_DESCRIPTION};
                                itemAdapter = new SimpleCursorAdapter(getBaseContext(), android.R.layout.two_line_list_item,
                                        itemCursor, headers, new int[]{android.R.id.text1, android.R.id.text2}, 0);
                                itemList.setAdapter(itemAdapter);
                                itemAdapter.notifyDataSetChanged();
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