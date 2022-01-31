package by.govoronok.thirdapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import by.govoronok.thirdapp.R;
import by.govoronok.thirdapp.model.User;

public class UserInfo extends AppCompatActivity {

    User user;
    TextView name, surname, email, phone, country, city, social;
    ImageView photo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

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

    public void goToWeb(View view) {
        Uri webpage = Uri.parse("http://www."+user.getSocial());
        Intent webIntent = new Intent(Intent.ACTION_VIEW, webpage);
        if (webIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(webIntent);
        }
    }

    public void goToCall(View view){
        Intent callIntent = new Intent(Intent.ACTION_DIAL);
        callIntent.setData(Uri.parse("tel:" + user.getPhone()));
        if (callIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(callIntent);
        }
    }

    public void goToMail(View view){
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[] {user.getEmail()}); // recipients
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Email subject");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Email message text");
        emailIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse("content://path/to/email/attachment"));

        startActivity(emailIntent);
    }
}