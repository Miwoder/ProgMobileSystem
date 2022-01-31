package by.govoronok.thirdapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import by.govoronok.thirdapp.JSONHelper;
import by.govoronok.thirdapp.R;
import by.govoronok.thirdapp.model.User;

public class ConfirmActivity extends AppCompatActivity {

    User user;
    TextView name, surname, email, phone, country, city, social;
    ImageView photo;
    private List<User> users;
    final static String userVariableKey = "USER_VARIABLE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm);

        name = (TextView) findViewById(R.id.nameConfirm);
        surname = (TextView) findViewById(R.id.surnameConfirm);
        email = (TextView) findViewById(R.id.emailConfirm);
        phone = (TextView) findViewById(R.id.phoneConfirm);
        country = (TextView) findViewById(R.id.countryConfirm);
        city = (TextView) findViewById(R.id.cityConfirm);
        social = (TextView) findViewById(R.id.socialConfirm);
        photo = (ImageView) findViewById(R.id.photo);

        Bundle arguments = getIntent().getExtras();
        if(arguments!=null){
            user = (User) arguments.getSerializable(User.class.getSimpleName());
            name.setText(user.getName());
            surname.setText(user.getSurname());
            email.setText(user.getEmail());
            phone.setText(user.getPhone());
            country.setText(user.getCountry());
            city.setText(user.getCity());
            social.setText(user.getSocial());
        }

        try {
            File file=new File(user.getPicture());
            photo.setImageBitmap(BitmapFactory.decodeStream(new FileInputStream(file)));
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
    }

    public void confirmClick(View view) {
        users = new ArrayList<User>();
        users = JSONHelper.importFromJSON(this);
        users.add(user);
        boolean result = JSONHelper.exportToJSON(this, users);
        if(result){
            Toast.makeText(this, "Данные сохранены", Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(this, "Не удалось сохранить данные", Toast.LENGTH_LONG).show();
        }
        Intent intent = new Intent(this, AllUsers.class);
        startActivity(intent);

    }

    public void backToThird(View view) {
        Intent intent = new Intent(this, ThirdActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        intent.putExtra(User.class.getSimpleName(), user);
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