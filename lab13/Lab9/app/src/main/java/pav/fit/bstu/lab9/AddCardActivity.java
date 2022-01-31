package pav.fit.bstu.lab9;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.icu.util.Calendar;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.format.DateUtils;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProvider;

import org.jetbrains.annotations.Nullable;

import java.io.ByteArrayOutputStream;
import java.util.List;

public class AddCardActivity extends AppCompatActivity {

    public static final String PAR_ID = "PAR_ID";
    public static final String PAR_NAME = "PAR_NAME";
    public static final String PAR_WORK = "PAR_WORK";
    public static final String PAR_EMAIL = "PAR_EMAIL";
    public static final String PAR_PHOTO = "PAR_PHOTO";
    public static final String PAR_MOBILE = "PAR_MOBILE";
    public static final String PAR_LINK = "PAR_LINK";

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    private static int RESULT_LOAD_IMAGE = 1;
    TextView title;
    EditText editName;
    EditText editWork;
    EditText editEmail;
    EditText editMobile;
    EditText editLink;
    ImageView pic;
    Button butPic;
    Bitmap bm;
    String encodedImage;
    public List<ContactCard> col;
    Toast toast;
    Bundle args;
    Button butCreate;

    @Override
    protected void onPause() {
        // Whenever this activity is paused (i.e. looses focus because another activity is started etc)
        // Override how this activity is animated out of view
        // The new activity is kept still and this activity is pushed out to the left
        overridePendingTransition(R.anim.hold, R.anim.push_out_to_left);
        super.onPause();
    }



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.pull_in_from_left, R.anim.hold);
        setContentView(R.layout.activity_add_card);

        args = getIntent().getExtras();
        title = (TextView) findViewById(R.id.textViewTitle);
        editName = (EditText) findViewById(R.id.editTextName);
        editWork = (EditText) findViewById(R.id.editTextWork);
        editEmail = (EditText) findViewById(R.id.editTextEmail);
        editMobile = (EditText) findViewById(R.id.editTextMobile);
        editLink = (EditText) findViewById(R.id.editTextLink);
        butCreate = (Button) findViewById(R.id.buttonEditTask);
        pic = (ImageView) findViewById(R.id.imageView);
        butPic = (Button) findViewById(R.id.buttonPic);
        butPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                Intent loadIntent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(loadIntent, RESULT_LOAD_IMAGE);
            }
        });

        if (args != null) {
            butCreate.setText("Edit");
            title.setText("Edit card");
            editName.setText(args.get(PAR_NAME).toString());
            editWork.setText(args.get(PAR_WORK).toString());
            editEmail.setText(args.get(PAR_EMAIL).toString());
            editMobile.setText(args.get(PAR_MOBILE).toString());
            editLink.setText(args.get(PAR_LINK).toString());
            encodedImage = args.get(PAR_PHOTO).toString();
            byte[] decodedMessage = android.util.Base64.decode(args.get(PAR_PHOTO).toString(), Base64.DEFAULT);
            Bitmap bm = BitmapFactory.decodeByteArray(decodedMessage, 0, decodedMessage.length);
            pic.setImageBitmap(bm);
        }
    }

    public void createNewTask(View view) {
        if (validate()) {
            CardViewModel cardViewModel = new ViewModelProvider(this).get(CardViewModel.class);
            if (args == null) {
                ContactCard card = new ContactCard(editName.getText().toString(), encodedImage, editWork.getText().toString(), editEmail.getText().toString(),
                        editMobile.getText().toString(), editLink.getText().toString());
                cardViewModel.insert(card);
            } else {
                ContactCard card = new ContactCard(editName.getText().toString(), encodedImage, editWork.getText().toString(), editEmail.getText().toString(),
                        editMobile.getText().toString(), editLink.getText().toString());
                card.setId(args.getString(PAR_ID));
                cardViewModel.update(card);
            }

            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(intent);
        } else {
            toast = Toast.makeText(this, "Wrong form!", Toast.LENGTH_LONG);
            toast.show();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            verifyStoragePermissions(this);
            bm = BitmapFactory.decodeFile(picturePath);
            pic.setImageBitmap(bm);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bm.compress(Bitmap.CompressFormat.JPEG, 100, baos); // bm is the bitmap object
            byte[] b = baos.toByteArray();
            encodedImage = Base64.encodeToString(b, Base64.DEFAULT);

        }
    }

    public static void verifyStoragePermissions(AppCompatActivity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }

    public boolean validate() {
        if (editName.getText().length() != 0 && editWork.getText().length() != 0 && editEmail.getText().length() != 0 &&
                editEmail.getText().length() != 0 && editMobile.getText().length() != 0 && !encodedImage.isEmpty()) {
            return true;
        }
        return false;
    }


    public void backToList(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }

}
