package com.gallosalocin.mareu.ui;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;

import com.gallosalocin.mareu.R;
import com.gallosalocin.mareu.model.Reunion;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddReunionActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    @BindView(R.id.tv_add_reunion_activity_time)
    TextView timeTextView;
    @BindView(R.id.spinner_add_reunion_activity_room)
    Spinner roomsSpinner;
    @BindView(R.id.et_add_reunion_activity_topic)
    TextInputLayout topicInputText;
    @BindView(R.id.et_add_reunion_activity_email)
    TextInputLayout emailInputText;
    @BindView(R.id.iv_add_reunion_activity_image)
    ImageView imageImageView;

    List<Reunion> reunionList;
    ReunionRecycleViewAdapter reunionRecycleViewAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reunion);
        ButterKnife.bind(this);

        configTimePicker();
        configSpinner();

    }

    public void configTimePicker() {

        Calendar calendar = Calendar.getInstance();

        final int hour = calendar.get(Calendar.HOUR_OF_DAY);
        final int minute = calendar.get(Calendar.MINUTE);

        timeTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                TimePickerDialog timePickerDialog = new TimePickerDialog(AddReunionActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        timeTextView.setText(checkDigit(hourOfDay) + "h" + checkDigit(minute));
                    }
                }, hour, minute, DateFormat.is24HourFormat(AddReunionActivity.this));
                timePickerDialog.show();
            }
        });
    }

    public String checkDigit(int number) {
        return number <= 9 ? "0" + number : String.valueOf(number);
    }

    public void configSpinner() {
        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(this, R.array.rooms, R.layout.spinner_custom_phone_portrait_layout);
        arrayAdapter.setDropDownViewResource(R.layout.spinner_custom_phone_portrait_layout);
        roomsSpinner.setAdapter(arrayAdapter);
        roomsSpinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (parent.getItemAtPosition(position).equals("Choose Room")){
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    private boolean validateTime() {
        String timeInput = timeTextView.getText().toString().trim();

        if (timeInput.isEmpty()){
            timeTextView.setError("Field can't be empty");
            return false;
        } else{
            timeTextView.setError(null);
            return true;
        }
    }

    private boolean validateTopic(){
        String topicInput = topicInputText.getEditText().getText().toString().trim();

        if (topicInput.isEmpty()){
            topicInputText.setError("Field can't be empty");
            return false;
        } else{
            topicInputText.setError(null);
            return true;
        }
    }

    private boolean validateEmail() {
        String emailInput = emailInputText.getEditText().getText().toString().trim();

        if (emailInput.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) {
            emailInputText.setError("Please enter a valid email address");
            return false;
        } else {
            emailInputText.setError(null);
            return true;
        }
    }

    public void saveInput(View view) {
        if (!validateEmail() | !validateTopic() | validateTime()){
            return;
        }
    }
}