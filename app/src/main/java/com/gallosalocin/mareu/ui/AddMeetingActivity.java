package com.gallosalocin.mareu.ui;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
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
import androidx.fragment.app.DialogFragment;

import com.gallosalocin.mareu.R;
import com.gallosalocin.mareu.di.DI;
import com.gallosalocin.mareu.model.Meeting;
import com.gallosalocin.mareu.service.MeetingApiService;
import com.gallosalocin.mareu.utils.Room;
import com.gallosalocin.mareu.utils.TimePickerFragment;
import com.google.android.material.textfield.TextInputLayout;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddMeetingActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, TimePickerDialog.OnTimeSetListener {

    @BindView(R.id.tv_add_reunion_activity_time)
    TextView timeTextView;
    @BindView(R.id.spinner_add_reunion_activity_room)
    Spinner roomsSpinner;
    @BindView(R.id.ti_add_reunion_activity_topic)
    TextInputLayout topicInputText;
    @BindView(R.id.ti_add_reunion_activity_email)
    TextInputLayout emailInputText;
    @BindView(R.id.iv_add_reunion_activity_room_color)
    ImageView roomColorImageView;
    @BindView(R.id.btn_add_reunion_activity_save)
    Button saveButton;

    private MeetingApiService meetingApiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_meeting);
        ButterKnife.bind(this);

        configTimePicker();
        configSpinner();
        saveMeeting();

        meetingApiService = DI.getMeetingApiService();               //  ???
    }

    // CONFIGURATION TimePicker

    public void configTimePicker() {

        timeTextView.setOnClickListener(view -> {
            DialogFragment timePicker = new TimePickerFragment();
            timePicker.show(getSupportFragmentManager(), "time picker");
        });
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        timeTextView.setText(checkDigitTimePicker(hourOfDay) + "h" + checkDigitTimePicker(minute));

    }

    public String checkDigitTimePicker(int number) {
        return number <= 9 ? "0" + number : String.valueOf(number);
    }

    // CONFIGURATION Spinner

    public void configSpinner() {

        List<Room> roomList = new ArrayList<>();
        roomList.add(new Room("- Choose Room -", R.drawable.logo_mareu));
        roomList.add(new Room("Room A", R.drawable.ic_lens_blue));
        roomList.add(new Room("Room B", R.drawable.ic_lens_cyan));
        roomList.add(new Room("Room C", R.drawable.ic_lens_blue_marine));
        roomList.add(new Room("Room D", R.drawable.ic_lens_green));
        roomList.add(new Room("Room E", R.drawable.ic_lens_orange));
        roomList.add(new Room("Room F", R.drawable.ic_lens_pink));
        roomList.add(new Room("Room G", R.drawable.ic_lens_purple));
        roomList.add(new Room("Room H", R.drawable.ic_lens_red));
        roomList.add(new Room("Room I", R.drawable.ic_lens_violet));
        roomList.add(new Room("Room J", R.drawable.ic_lens_yellow));

        ArrayAdapter<Room> adapter = new ArrayAdapter<>(this, R.layout.spinner_custom_phone_portrait_layout, roomList);
        adapter.setDropDownViewResource(R.layout.spinner_custom_phone_portrait_layout);
        roomsSpinner.setAdapter(adapter);
        roomsSpinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Room room = (Room) parent.getItemAtPosition(position);
        roomColorImageView.setImageResource(room.getRoomColor());
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    // CONFIGURATION SaveButton

    private boolean validateRoom() {
        String roomInput = roomsSpinner.getSelectedItem().toString().trim();

        if (roomInput.equals("- Choose Room -")) {
            ((TextView) roomsSpinner.getSelectedView()).setError("Error message");
            return false;
        } else {
            ((TextView) roomsSpinner.getSelectedView()).setError(null);
            return true;
        }
    }

    private boolean validateTime() {
        String timeInput = timeTextView.getText().toString().trim();

        if (timeInput.isEmpty()) {
            timeTextView.setError("Field can't be empty");
            return false;
        } else {
            timeTextView.setError(null);
            return true;
        }
    }

    private boolean validateTopic() {
        String topicInput = topicInputText.getEditText().getText().toString().trim();

        if (topicInput.isEmpty()) {
            topicInputText.setError("Field can't be empty");
            return false;
        } else {
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

    public void saveMeeting() {
        saveButton.setOnClickListener(view -> {
            if (!validateRoom() | !validateEmail() | !validateTopic() | !validateTime()) {
            } else {
                Meeting meeting = new Meeting(roomColorImageView.getImageAlpha(), topicInputText.getEditText().getText().toString(), timeTextView.getText().toString(), roomsSpinner.getSelectedItem().toString(), emailInputText.getEditText().getText().toString());
                Intent intent = new Intent(AddMeetingActivity.this, MainActivity.class);
                meetingApiService.createMeeting(meeting);
                intent.putExtra("meeting", Parcels.wrap(meeting));
                startActivity(intent);
            }
        });
    }
}