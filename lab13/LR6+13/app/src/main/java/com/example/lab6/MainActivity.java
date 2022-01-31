package com.example.lab6;

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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements RecipeListFragment.RecipeListListener {

    CustomAdapter adapter;
    List<ModelRecipe> modelRecipeList;
    ListView listView;
    RecipeListFragment recipeListFragment;
TextView an;

    public static final int IDM_OPEN = 101;
    public static final int IDM_EDIT = 102;
    public static final int IDM_DELETE = 103;

    //13
    public static final int IDM_ALPHA = 104;
    public static final int IDM_ROTATE = 105;
    public static final int IDM_SCALE = 106;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        setContentView(R.layout.activity_main);



        modelRecipeList = JSONHelper.importFromJSON(this);
        recipeListFragment = (RecipeListFragment) getSupportFragmentManager().findFragmentById(R.id.list_frag);
        //registerForContextMenu(recipeListFragment.getListView());

        //13////////////
        an = (TextView) findViewById(R.id.an);
        registerForContextMenu(an);
        // Получим ссылку на часы
        ImageView clockImageView = findViewById(R.id.clock);
// анимация для вращения часов
        Animation clockTurnAnimation = AnimationUtils.loadAnimation(this, R.anim.clock_turn);
        clockImageView.startAnimation(clockTurnAnimation);
// получим ссылку на часовую стрелку
        ImageView hourImageView = findViewById(R.id.hour_hand);
// анимация для стрелки
        Animation hourTurnAnimation = AnimationUtils.loadAnimation(this, R.anim.hour_turn);
// присоединяем анимацию
        hourImageView.startAnimation(hourTurnAnimation);

    }
    @Override
    public void itemClicked(int position) {
        View fragmentContainer = findViewById(R.id.detail_frame);
        if(fragmentContainer!=null){
            RecipeDetailFragment fragm = new RecipeDetailFragment();
            FragmentTransaction ftr = getSupportFragmentManager().beginTransaction();
            fragm.setTask(position);
            //ftr.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            ftr.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_in_right);
            ftr.replace(R.id.detail_frame, fragm);
            ftr.addToBackStack(null);
            ftr.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ftr.commit();
        }
        else{
            ModelRecipe selectedModelRecipe = modelRecipeList.get(position);
            Intent intent = new Intent(this, InspectActivity.class);
            intent.putExtra(ModelRecipe.class.getSimpleName(), selectedModelRecipe);
            startActivity(intent);
        }

    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        switch (v.getId()) {
            case R.id.an:
                menu.add(Menu.NONE, IDM_ALPHA, Menu.NONE, "Alpha");
                menu.add(Menu.NONE, IDM_ROTATE, Menu.NONE, "Rotate");
                menu.add(Menu.NONE, IDM_SCALE, Menu.NONE, "Scale");
                break;
        }

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
//        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
//        ModelRecipe selectedModelRecipe = modelRecipeList.get(info.position);
       //13
        Animation anim = null;
        switch (item.getItemId()) {
//            case IDM_OPEN:
//                Intent inspectIntent = new Intent(getBaseContext(), InspectActivity.class);
//                inspectIntent.putExtra(ModelRecipe.class.getSimpleName(), selectedModelRecipe);
//                startActivity(inspectIntent);
//                break;
//            case IDM_EDIT:
//                editItem(info.position);
//                break;
//            case IDM_DELETE:
//                new AlertDialog.Builder(MainActivity.this)
//                        .setTitle("Remove recipe")
//                        .setMessage("Are you sure?")
//                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int which) {
//                                modelRecipeList.remove(info.position);
//                                JSONHelper.exportToJSON(MainActivity.this,modelRecipeList);
//                                adapter = new CustomAdapter(recipeListFragment.getContext(), R.layout.custom_listview, modelRecipeList);
//                                recipeListFragment.setListAdapter(adapter);
//                                adapter.notifyDataSetChanged();
//                            }
//                        })
//                        .setNegativeButton("Cancel", null)
//                        .show();
//                break;
            //13
            case IDM_ALPHA:
                // создаем объект анимации из файла anim/myalpha
                anim = AnimationUtils.loadAnimation(this, R.anim.myalpha);
                break;
            case IDM_SCALE:
                anim = AnimationUtils.loadAnimation(this, R.anim.myscale);
                break;
            case IDM_ROTATE:
                anim = AnimationUtils.loadAnimation(this, R.anim.myrotate);
                break;
            default:
                return super.onContextItemSelected(item);
        }
        an.startAnimation(anim);
        return true;
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
            case R.id.action_add :
                Intent intent = new Intent(getBaseContext(), CreateActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_clearList:
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Remove all recipes")
                        .setMessage("Are you sure?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                modelRecipeList.clear();
//                                recipes.remove(position);
                                JSONHelper.exportToJSON(MainActivity.this,modelRecipeList);
                                adapter = new CustomAdapter(recipeListFragment.getContext(), R.layout.custom_listview, modelRecipeList);
                                recipeListFragment.setListAdapter(adapter);
                                adapter.notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .show();
                return true;
            case R.id.action_count:
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Recipes")
                        .setMessage("Count of recipes: " + modelRecipeList.size())
                        .setPositiveButton("Ок", null)
                        .show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public void editItem(int pos){
        Intent intent = new Intent(this, EditActivity.class);
        intent.putExtra("pos", pos);
        startActivity(intent);
    }
}