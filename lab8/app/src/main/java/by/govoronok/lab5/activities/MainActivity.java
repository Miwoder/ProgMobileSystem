package by.govoronok.lab5.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
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

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import by.govoronok.lab5.CustomAdapter;
import by.govoronok.lab5.model.Item;
import by.govoronok.lab5.JSONHelper;
import by.govoronok.lab5.R;
import by.govoronok.lab5.repository.DatabaseHelper;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private List<Item> items;
    DatabaseHelper databaseHelper;
    SQLiteDatabase db;
    Cursor itemCursor;
    CustomAdapter adapter;

    private RecyclerView recyclerView;

    public static final int IDM_OPEN = 101;
    public static final int IDM_EDIT = 102;
    public static final int IDM_DELETE = 103;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NavigationView navigationView = (NavigationView) findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(this);


        databaseHelper = new DatabaseHelper(getApplicationContext());
        db = databaseHelper.getReadableDatabase();
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


        recyclerView = findViewById(R.id.list);
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(this, recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Item selectedItem = items.get(position);
                        Intent intent = new Intent(getBaseContext(), ItemInfo.class);
                        intent.putExtra(Item.class.getSimpleName(), selectedItem);
                        startActivity(intent);
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(layoutManager);
        } else {
            GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
            recyclerView.setLayoutManager(layoutManager);
        }
        adapter = new CustomAdapter(this, items);
        recyclerView.setAdapter(adapter);
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
        itemCursor =  db.rawQuery("select * from "+ DatabaseHelper.TABLE, null);
        itemCursor.moveToPosition(item.getGroupId());
        Item selectedItem = new Item();
        selectedItem.setId(itemCursor.getInt(itemCursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ID)));
        selectedItem.setName(itemCursor.getString(itemCursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_NAME)));
        selectedItem.setDescription(itemCursor.getString(itemCursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_DESCRIPTION)));
        selectedItem.setReturningPlace(itemCursor.getString(itemCursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_RETURNINGPLACE)));
        selectedItem.setFoundDate(itemCursor.getString(itemCursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_FOUNDDATE)));
        selectedItem.setPicture(itemCursor.getString(itemCursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_PICTURE)));
        selectedItem.setFoundPlace(itemCursor.getString(itemCursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_FOUNDPLACE)));


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
                                adapter = new CustomAdapter(getBaseContext(), items);
                                recyclerView.setAdapter(adapter);
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
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawerLayout);
        switch (item.getItemId()) {
            case R.id.create:
                Intent intent = new Intent(getBaseContext(), AddNew.class);
                startActivity(intent);
                drawer.closeDrawer(GravityCompat.START);
                return true;
            case R.id.sortById:
                itemCursor = db.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE + " ORDER BY " + DatabaseHelper.COLUMN_ID, null);
                itemCursor.moveToFirst();
                items.clear();
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
                adapter = new CustomAdapter(getBaseContext(), items);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                drawer.closeDrawer(GravityCompat.START);
                return true;
            case R.id.sortByName:
                itemCursor = db.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE + " ORDER BY " + DatabaseHelper.COLUMN_NAME, null);
                itemCursor.moveToFirst();
                items.clear();
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
                adapter = new CustomAdapter(getBaseContext(), items);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                drawer.closeDrawer(GravityCompat.START);
                return true;
            case R.id.action_set_linear_layout:
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
                recyclerView.setLayoutManager(linearLayoutManager);
                drawer.closeDrawer(GravityCompat.START);
                return true;
            case R.id.action_set_grid_layout:
                GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
                recyclerView.setLayoutManager(gridLayoutManager);
                drawer.closeDrawer(GravityCompat.START);
                return true;
        }
        return true;
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        // Закрываем подключение и курсор
        db.close();
        itemCursor.close();
    }

}