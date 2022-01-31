package com.varets.lab101;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

public class AddEditCaseActivity extends AppCompatActivity {

    private EditText editTextName;
    private EditText editTextLocation;
    private EditText editTextDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_case);
        editTextDate = findViewById(R.id.edit_text_date);
        editTextName = findViewById(R.id.edit_text_name);
        editTextLocation = findViewById(R.id.edit_text_location);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);

        Intent intent = getIntent();
        if(intent.hasExtra("id")){
            setTitle("Edit case");
            editTextName.setText(intent.getStringExtra("name"));
            editTextLocation.setText(intent.getStringExtra("location"));
            editTextDate.setText(intent.getStringExtra("date"));
        }
        else {
            setTitle("Add case");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_note_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_case:
                saveCase();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void saveCase() {
        String name = editTextName.getText().toString();
        String date = editTextDate.getText().toString();
        String location = editTextLocation.getText().toString();
        if(name.trim().isEmpty()||location.trim().isEmpty()){
            Toast.makeText(this,"data not correct", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent data = new Intent();
        data.putExtra("name",name);
        data.putExtra("location",location);
        data.putExtra("date",date);

        int id = getIntent().getIntExtra("id",-1);
        if(id!=-1){
            data.putExtra("id",id);
        }
        setResult(RESULT_OK,data);
        finish();
    }
}