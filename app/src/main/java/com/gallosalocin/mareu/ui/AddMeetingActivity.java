package com.gallosalocin.mareu.ui;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.gallosalocin.mareu.R;
import com.gallosalocin.mareu.databinding.ActivityAddMeetingBinding;
import com.gallosalocin.mareu.di.DI;
import com.gallosalocin.mareu.model.Meeting;
import com.gallosalocin.mareu.service.MeetingApiService;
import com.gallosalocin.mareu.utils.Room;
import com.gallosalocin.mareu.utils.TimePickerFragment;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipDrawable;
import com.google.android.material.textfield.TextInputEditText;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AddMeetingActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, TimePickerDialog.OnTimeSetListener {

    private MeetingApiService meetingApiService;

    private ActivityAddMeetingBinding binding;

    private TextView textViewTime;
    private TextInputEditText textInputEditTextEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAddMeetingBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        textViewTime = findViewById(R.id.text_view_time);
        textInputEditTextEmail = findViewById(R.id.text_input_email);
        textInputEditTextEmail.setOnEditorActionListener(editorListener);

        configTimePicker();
        configSpinner();
        saveMeeting();

        meetingApiService = DI.getMeetingApiService();

    }

    // CONFIGURATION Chip

    private void configChip() {
        Chip chip = new Chip(AddMeetingActivity.this);
        ChipDrawable drawable = ChipDrawable.createFromAttributes(AddMeetingActivity.this, null, 0, R.style.Widget_MaterialComponents_Chip_Entry);
        chip.setChipDrawable(drawable);
        chip.setClickable(false);
        chip.setText(textInputEditTextEmail.getText().toString().trim());
        chip.setOnCloseIconClickListener(view -> {
            Objects.requireNonNull(binding.chipGroup).removeView(chip);
        });
        Objects.requireNonNull(binding.chipGroup).addView(chip);
        textInputEditTextEmail.setText("");
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

    // CONFIGURATION TimePicker

    public void configTimePicker() {
        textViewTime.setOnClickListener(view -> {
            DialogFragment timePicker = new TimePickerFragment();
            timePicker.show(getSupportFragmentManager(), "time picker");
        });
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        textViewTime.setText(String.format("%sh%s", checkDigitTimePicker(hourOfDay), checkDigitTimePicker(minute)));
    }

    public String checkDigitTimePicker(int number) {
        return number <= 9 ? "0" + number : String.valueOf(number);
    }

    // CONFIGURATION Spinner

    public void configSpinner() {

        List<Room> roomList = new ArrayList<>();
        roomList.add(new Room("- Choose Room -", R.drawable.logo_mareu));
        roomList.add(new Room("Room A", R.drawable.ic_lens_blue));
        roomList.add(new Room("Room B", R.drawable.ic_lens_yellow));
        roomList.add(new Room("Room C", R.drawable.ic_lens_blue_marine));
        roomList.add(new Room("Room D", R.drawable.ic_lens_green));
        roomList.add(new Room("Room E", R.drawable.ic_lens_orange));
        roomList.add(new Room("Room F", R.drawable.ic_lens_pink));
        roomList.add(new Room("Room G", R.drawable.ic_lens_purple));
        roomList.add(new Room("Room H", R.drawable.ic_lens_red));
        roomList.add(new Room("Room I", R.drawable.ic_lens_violet));
        roomList.add(new Room("Room J", R.drawable.ic_lens_cyan));

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

    // CONFIGURATION SaveButton

    private boolean validateRoom() {
        String roomInput = binding.spinnerRoom.getSelectedItem().toString().trim();

        if (roomInput.equals("- Choose Room -")) {
            ((TextView) binding.spinnerRoom.getSelectedView()).setError("Error message");
            return false;
        } else {
            ((TextView) binding.spinnerRoom.getSelectedView()).setError(null);
            return true;
        }
    }

    private boolean validateTime() {
        String timeInput = textViewTime.getText().toString().trim();

        if (timeInput.isEmpty()) {
            textViewTime.setError("Field can't be empty");
            return false;
        } else {
            textViewTime.setError(null);
            return true;
        }
    }

    private boolean validateTopic() {
        String topicInput = binding.textInputTopicLayout.getEditText().getText().toString().trim();

        if (topicInput.isEmpty()) {
            binding.textInputTopicLayout.setError("Field can't be empty");
            return false;
        } else {
            binding.textInputTopicLayout.setError(null);
            return true;
        }
    }


    private boolean validateEmail() {
        String emailInput = binding.textInputEmailLayout.getEditText().getText().toString().trim();

        if (emailInput.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) {
            binding.textInputEmailLayout.setError("Please enter a valid email address");
            return false;
        } else {
            binding.textInputEmailLayout.setError(null);
            return true;
        }
    }

    public void saveMeeting() {
        binding.buttonSave.setOnClickListener(view -> {
            if (!validateRoom() | !validateTopic() | !validateTime() | !validateEmail()) {
            } else {
                Meeting meeting = new Meeting((int) binding.imageViewRoomColor.getTag(), binding.textInputTopicLayout.getEditText().getText().toString(), textViewTime.getText().toString(), binding.spinnerRoom.getSelectedItem().toString(), binding.chipGroup.toString());
                Intent intent = new Intent(AddMeetingActivity.this, MainActivity.class);
                meetingApiService.createMeeting(meeting);
                intent.putExtra("meeting", Parcels.wrap(meeting));
                startActivity(intent);
            }
        });
    }


}