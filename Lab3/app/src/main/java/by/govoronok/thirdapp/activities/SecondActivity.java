package by.govoronok.thirdapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;

import by.govoronok.thirdapp.R;
import by.govoronok.thirdapp.model.User;

public class SecondActivity extends AppCompatActivity {

    TextInputLayout countryText, cityText;
    TextView helloName;
    User user;
    final static String userVariableKey = "USER_VARIABLE";

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        helloName = (TextView) findViewById(R.id.helloView);
        countryText = (TextInputLayout) findViewById(R.id.countryText);
        cityText = (TextInputLayout) findViewById(R.id.cityText);

        Bundle arguments = getIntent().getExtras();
        if(arguments!=null){
            user = (User) arguments.getSerializable(User.class.getSimpleName());
            helloName.setText("Hello, " + user.getName() + " " + user.getSurname());
        }
    }

    public void nextFromSecond(View view) {
        String country = countryText.getEditText().getText().toString();
        String city = cityText.getEditText().getText().toString();

        user.setCity(city);
        user.setCountry(country);

        Intent intent = new Intent(this, ThirdActivity.class);
        intent.putExtra(User.class.getSimpleName(), user);
        startActivity(intent);
    }

    public void backFromSecond(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        intent.putExtra(User.class.getSimpleName(), user);
        startActivity(intent);
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putSerializable(userVariableKey, user);
        outState.putString("city", cityText.getEditText().getText().toString());
        outState.putString("country", countryText.getEditText().getText().toString());
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        user = (User)savedInstanceState.getSerializable(userVariableKey);
        countryText.getEditText().setText(savedInstanceState.getString("country"));
        cityText.getEditText().setText(savedInstanceState.getString("city"));
    }

}