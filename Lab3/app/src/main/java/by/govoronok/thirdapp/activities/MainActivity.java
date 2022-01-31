package by.govoronok.thirdapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import by.govoronok.thirdapp.R;
import by.govoronok.thirdapp.model.User;

public class MainActivity extends AppCompatActivity {

    EditText nameText, surnameText;
    EditText  phoneText, emailText;
    final static String userVariableKey = "USER_VARIABLE";
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void nextFromFirst(View view) {
        nameText = (EditText) findViewById(R.id.name);
        surnameText = (EditText) findViewById(R.id.surname);
        phoneText = (EditText) findViewById(R.id.editTextTextEmailAddress);
        emailText = (EditText) findViewById(R.id.editTextNumberSigned);

        String name = nameText.getText().toString();
        String surname = surnameText.getText().toString();

        user = new User(name , surname, phoneText.getText().toString(), emailText.getText().toString());

        Intent intent = new Intent(this, SecondActivity.class);
        intent.putExtra(User.class.getSimpleName(), user);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }

    public void goToUsers(View view) {
        Intent intent = new Intent(this, AllUsers.class);
        startActivity(intent);
    }

    // сохранение состояния
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putSerializable(userVariableKey, user);
        super.onSaveInstanceState(outState);
    }
    // получение ранее сохраненного состояния
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        // получаем объект User в переменную
        user = (User)savedInstanceState.getSerializable(userVariableKey);
    }
}