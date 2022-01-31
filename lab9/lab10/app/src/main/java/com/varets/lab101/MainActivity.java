package com.varets.lab101;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.varets.lab101.db.Entities.ModelCases;
import com.varets.lab101.ui.main.MainViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final int ADD_CASE_REQUEST = 1;
    public static final int EDIT_CASE_REQUEST = 2;
    private MainViewModel mViewModel;
    public RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        FloatingActionButton button = findViewById(R.id.button_add_case);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddEditCaseActivity.class);
                startActivityForResult(intent, ADD_CASE_REQUEST);
            }
        });

        recyclerView = findViewById(R.id.recycle_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final CasesAdapter adapter = new CasesAdapter();
        recyclerView.setAdapter(adapter);

        mViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        mViewModel.getAllCases().observe(this, new Observer<List<ModelCases>>() {
            @Override
            public void onChanged(List<ModelCases> modelCases) {
                adapter.setCases(modelCases);
            }
        });
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                mViewModel.delete(adapter.getCaseAt(viewHolder.getAdapterPosition()));
                Toast.makeText(getApplicationContext(), "Case deleted", Toast.LENGTH_SHORT).show();

            }
        }).attachToRecyclerView(recyclerView);
        adapter.setOnItemClickListener(new CasesAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(ModelCases modelCases) {
                Intent intent = new Intent(MainActivity.this, AddEditCaseActivity.class);
                intent.putExtra("id", modelCases.getId());
                intent.putExtra("name", modelCases.getNameCase());
                intent.putExtra("location", modelCases.getLocationCase());
                intent.putExtra("date", modelCases.getDateCase());
                startActivityForResult(intent, EDIT_CASE_REQUEST);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_CASE_REQUEST && resultCode == RESULT_OK) {
            String name = data.getStringExtra("name");
            String location = data.getStringExtra("location");
            String date = data.getStringExtra("date");
            ModelCases modelCases = new ModelCases(name, location, date);
            mViewModel.insert(modelCases);
            Toast.makeText(getApplicationContext(), "case saved", Toast.LENGTH_SHORT).show();
        } else if (requestCode == EDIT_CASE_REQUEST && resultCode == RESULT_OK) {
            int id = data.getIntExtra("id", -1);
            if (id == -1) {
                Toast.makeText(getApplicationContext(), "Case can't be updated", Toast.LENGTH_SHORT).show();
                return;
            }
            String name = data.getStringExtra("name");
            String location = data.getStringExtra("location");
            String date = data.getStringExtra("date");
            ModelCases modelCases = new ModelCases(name, location, date);
            modelCases.setId(id);
            mViewModel.update(modelCases);
            Toast.makeText(getApplicationContext(), "Case updated", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(getApplicationContext(), "case not saved", Toast.LENGTH_SHORT).show();
        }

    }
}