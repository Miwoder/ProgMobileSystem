package by.govoronok.labsec;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    EditText weightText, heightText, ageText;
    TextView resultText;
    RadioGroup sexRGroup;
    Spinner activityLevel;
    RadioButton femaleRButton, maleRButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initParameters();
    }

    protected void initParameters(){
        weightText = (EditText) findViewById(R.id.weight);
        heightText = (EditText) findViewById(R.id.height);
        ageText = (EditText) findViewById(R.id.age);
        resultText = (TextView) findViewById(R.id.resultTextView);
        sexRGroup = (RadioGroup) findViewById(R.id.radioGroup);
        femaleRButton = (RadioButton) findViewById(R.id.femaleRButton);
        maleRButton = (RadioButton)findViewById(R.id.maleRButton);

        String[] activityLevels = { "Low", "Medium", "High", "Sporty"};
        activityLevel = (Spinner) findViewById(R.id.activityLevelSpinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, activityLevels);
        // Определяем разметку для использования при выборе элемента
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        activityLevel.setAdapter(adapter);
    }

    @SuppressLint("SetTextI18n")
    public void calculateCalories(View view) {
        double BMR;
        if(femaleRButton.isChecked())
            BMR = 447.593 + (9.247 * Integer.parseInt(weightText.getText().toString())) +
                    (3.098 * Integer.parseInt(heightText.getText().toString())) - (4.330 * Integer.parseInt(ageText.getText().toString()));
        else
            BMR = 88.362 + (13.397 * Integer.parseInt(weightText.getText().toString())) +
                    (4.799 * Integer.parseInt(heightText.getText().toString())) - (5.677 * Integer.parseInt(ageText.getText().toString()));

        switch ((int) activityLevel.getSelectedItemId()) {
            case 0:
                resultText.setText("You need " + Double.toString(Math.round(BMR * 1.2)) + " calories");
                break;
            case 1:
                resultText.setText("You need " + Double.toString(Math.round(BMR * 1.55)) + " calories");
                break;
            case 2:
                resultText.setText("You need " + Double.toString(Math.round(BMR * 1.725)) + " calories");
                break;
            case 3:
                resultText.setText("You need " +Double.toString(Math.round(BMR * 1.9) )+ " calories");
                break;
            default:
                resultText.setText("Choose activity level!");
        }
    }
}