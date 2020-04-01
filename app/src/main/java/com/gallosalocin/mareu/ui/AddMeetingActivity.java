package com.gallosalocin.mareu.ui;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;

import com.gallosalocin.mareu.R;
import com.gallosalocin.mareu.databinding.ActivityAddMeetingBinding;
import com.gallosalocin.mareu.di.DI;
import com.gallosalocin.mareu.model.Meeting;
import com.gallosalocin.mareu.service.MeetingApiService;
import com.gallosalocin.mareu.utils.Room;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipDrawable;

import org.parceler.Parcels;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

public class AddMeetingActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener,
        TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener {

    private MeetingApiService meetingApiService;
    private ActivityAddMeetingBinding binding;
    private String emailChip = "";
    private Calendar calendar = Calendar.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAddMeetingBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        binding.textInputEmail.setOnEditorActionListener(editorListener);
        meetingApiService = DI.getMeetingApiService();

        configDatePicker();
        configTimePicker();
        configSpinner();
        cancelMeeting();
        saveMeeting();

    }

    // CONFIGURATION Spinner

    public void configSpinner() {

        List<Room> roomList = new ArrayList<>();
        roomList.add(new Room("- Choisir Salle -", R.drawable.logo_mareu));
        roomList.add(new Room("Salle A", R.drawable.ic_lens_blue));
        roomList.add(new Room("Salle B", R.drawable.ic_lens_yellow));
        roomList.add(new Room("Salle C", R.drawable.ic_lens_blue_marine));
        roomList.add(new Room("Salle D", R.drawable.ic_lens_green));
        roomList.add(new Room("Salle E", R.drawable.ic_lens_orange));
        roomList.add(new Room("Salle F", R.drawable.ic_lens_pink));
        roomList.add(new Room("Salle G", R.drawable.ic_lens_purple));
        roomList.add(new Room("Salle H", R.drawable.ic_lens_red));
        roomList.add(new Room("Salle I", R.drawable.ic_lens_violet));
        roomList.add(new Room("Salle J", R.drawable.ic_lens_cyan));

        ArrayAdapter<Room> adapter = new ArrayAdapter<>(this, R.layout.spinner_custom_phone_portrait_layout, roomList);
        adapter.setDropDownViewResource(R.layout.spinner_custom_phone_portrait_layout);
        binding.spinnerRoom.setAdapter(adapter);
        binding.spinnerRoom.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Room room = (Room) parent.getItemAtPosition(position);
        binding.imageViewRoomColor.setImageResource(room.getRoomColor());
        binding.imageViewRoomColor.setTag(room.getRoomColor());
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    // CONFIGURATION DatePicker

    private void configDatePicker() {
        binding.textViewDate.setOnClickListener(view -> showDatePickerDialog());
    }

    private void showDatePickerDialog() {
        DatePickerDialog datePicker = new DatePickerDialog(this, this, calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePicker.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        datePicker.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String currentDate = DateFormat.getDateInstance(DateFormat.DEFAULT).format(calendar.getTime());
        binding.textViewDate.setText(currentDate);
    }

    // CONFIGURATION TimePicker

    public void configTimePicker() {
        binding.textViewTime.setOnClickListener(view -> showTimePickerDialog());
    }

    private void showTimePickerDialog() {
        TimePickerDialog timePicker = new TimePickerDialog(this, this, calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE), android.text.format.DateFormat.is24HourFormat(this));
        timePicker.show();
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        binding.textViewTime.setText(getString(R.string.text_view_time, checkDigitTimePicker(hourOfDay),
                checkDigitTimePicker(minute)));
    }

    public String checkDigitTimePicker(int number) {
        return number <= 9 ? "0" + number : String.valueOf(number);
    }

    // CONFIGURATION Chip & Email

    private void configChip() {
        Chip chip = new Chip(AddMeetingActivity.this);
        ChipDrawable drawable = ChipDrawable.createFromAttributes(AddMeetingActivity.this, null, 0,
                R.style.Widget_MaterialComponents_Chip_Entry);
        chip.setChipDrawable(drawable);
        chip.setClickable(false);
        chip.setText(binding.textInputEmail.getText().toString().trim());
        chip.setOnCloseIconClickListener(view -> {
            Objects.requireNonNull(binding.chipGroup).removeView(chip);
        });
        Objects.requireNonNull(binding.chipGroup).addView(chip);
        emailChip += chip.getText() + ", ";
        binding.textInputEmail.setText("");
    }

    private EditText.OnEditorActionListener editorListener = (view, actionId, event) -> {
        String emailInput = binding.textInputEmailLayout.getEditText().getText().toString().trim();

        if (actionId == EditorInfo.IME_ACTION_DONE) {

            if (emailInput.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) {
                binding.textInputEmailLayout.setError("Please enter a valid email address");
                return false;
            } else {
                binding.textInputEmailLayout.setError(null);
                configChip();
                return false;
            }
        }
        return true;
    };

    // CONFIGURATION CancelButton

    private void cancelMeeting() {
        binding.buttonCancel.setOnClickListener(view -> {
            Intent intent = new Intent(AddMeetingActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });
    }

    // CONFIGURATION SaveButton

    private boolean validateRoom() {
        String roomInput = binding.spinnerRoom.getSelectedItem().toString().trim();

        if (roomInput.equals("- Choisir Salle -")) {
            ((TextView) binding.spinnerRoom.getSelectedView()).setError(getString(R.string.error_empty));
            return false;
        } else {
            ((TextView) binding.spinnerRoom.getSelectedView()).setError(null);
            return true;
        }
    }

    private boolean validateDate() {
        String dateInput = binding.textViewDate.getText().toString().trim();

        if (dateInput.isEmpty()) {
            binding.textViewDate.setError(getString(R.string.error_empty));
            return false;
        } else {
            binding.textViewDate.setError(null);
            return true;
        }
    }

    private boolean validateTime() {
        String timeInput = binding.textViewTime.getText().toString().trim();

        if (timeInput.isEmpty()) {
            binding.textViewTime.setError(getString(R.string.error_empty));
            return false;
        } else {
            binding.textViewTime.setError(null);
            return true;
        }
    }

    private boolean validateTopic() {
        String topicInput = binding.textInputTopicLayout.getEditText().getText().toString().trim();

        if (topicInput.isEmpty()) {
            binding.textInputTopicLayout.setError(getString(R.string.error_empty));
            return false;
        } else {
            binding.textInputTopicLayout.setError(null);
            return true;
        }
    }

    private boolean validateEmail() {
        if (emailChip.isEmpty()) {
            binding.textInputEmailLayout.setError(getString(R.string.error_not_valid));
            return false;
        } else {
            binding.textInputEmailLayout.setError(null);
            return true;
        }
    }

    @SuppressLint("ResourceAsColor")
    public void saveMeeting() {
        binding.buttonSave.setOnClickListener(view -> {
            if (!validateRoom() | !validateTopic() | !validateDate() | !validateTime() | !validateEmail()) {
                binding.buttonSave.setBackgroundTintList(this.getResources().getColorStateList(R.color.colorAccent));
            } else {
                emailChip = emailChip.substring(0, emailChip.length() - 2) + "";
                Meeting meeting = new Meeting((int) binding.imageViewRoomColor.getTag(), binding.textInputTopicLayout.getEditText().getText().toString(), binding.textViewDate.getText().toString(), binding.textViewTime.getText().toString(), binding.spinnerRoom.getSelectedItem().toString(), emailChip);
                Intent intent = new Intent(AddMeetingActivity.this, MainActivity.class);
                meetingApiService.createMeeting(meeting);
                intent.putExtra("meeting", Parcels.wrap(meeting));
                startActivity(intent);
                finish();
            }
        });
    }
}