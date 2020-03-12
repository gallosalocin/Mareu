package com.gallosalocin.mareu.ui;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.gallosalocin.mareu.R;
import com.gallosalocin.mareu.databinding.ActivityMainBinding;
import com.gallosalocin.mareu.di.DI;
import com.gallosalocin.mareu.model.Meeting;
import com.gallosalocin.mareu.service.MeetingApiService;
import com.gallosalocin.mareu.utils.DatePickerFragment;

import org.parceler.Parcels;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;

public class MainActivity extends AppCompatActivity implements MeetingRecyclerViewAdapter.OnItemClickListener, DatePickerDialog.OnDateSetListener {

    private List<Meeting> meetingList;
    private List<Meeting> meetingListByDate;
    private List<Meeting> meetingListByRoom;
    private MeetingRecyclerViewAdapter meetingRecyclerViewAdapter;
    private ActivityMainBinding binding;
    private MeetingApiService meetingApiService;
    private boolean stateRoom = true;
    private boolean stateTime = true;
    private Calendar calendar = Calendar.getInstance();
    private String currentDate;
    private int state = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        meetingApiService = DI.getMeetingApiService();
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        setSupportActionBar(binding.toolbar);

        configFabAddMeeting();
        initRecyclerView();
    }

    private void initRecyclerView() {
        Parcels.unwrap(getIntent().getParcelableExtra("meeting"));
        meetingList = meetingApiService.getMeetings();
        meetingRecyclerViewAdapter = new MeetingRecyclerViewAdapter(meetingList, this);
        binding.recyclerView.setAdapter(meetingRecyclerViewAdapter);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.filter_list_menu, menu);
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_toolbar_reset_filter:
                state = 0;
                initRecyclerView();
                return true;
            case R.id.item_toolbar_sort_name:
                sortListByRoom();
                return true;
            case R.id.item_toolbar_sort_time:
                sortListByTime();
                return true;
            case R.id.item_toolbar_filter_date:
                state = 1;
                configDatePicker();
                filterByDate();
                return true;
            case R.id.item_toolbar_filter_room:
                state = 2;
                return true;
            case R.id.subitem_room_a:
                filterByRoom("Room A");
                return true;
            case R.id.subitem_room_b:
                filterByRoom("Room B");
                return true;
            case R.id.subitem_room_c:
                filterByRoom("Room C");
                return true;
            case R.id.subitem_room_d:
                filterByRoom("Room D");
                return true;
            case R.id.subitem_room_e:
                filterByRoom("Room E");
                return true;
            case R.id.subitem_room_f:
                filterByRoom("Room F");
                return true;
            case R.id.subitem_room_g:
                filterByRoom("Room G");
                return true;
            case R.id.subitem_room_h:
                filterByRoom("Room H");
                return true;
            case R.id.subitem_room_i:
                filterByRoom("Room I");
                return true;
            case R.id.subitem_room_j:
                filterByRoom("Room J");
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void sortListByRoom() {
        initRecyclerView();
        meetingList.sort(stateRoom ? comparing(Meeting::getRoom) : comparing(Meeting::getRoom).reversed());
        stateRoom = !stateRoom;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void sortListByTime() {
        initRecyclerView();
        meetingList.sort(stateTime ? comparing(Meeting::getTime) : comparing(Meeting::getTime).reversed());
        stateTime = !stateTime;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void filterByDate() {
        initRecyclerView();
        meetingListByDate = meetingList.stream().filter(meeting -> meeting.getDate().equals(currentDate)).collect(Collectors.toList());
        meetingRecyclerViewAdapter = new MeetingRecyclerViewAdapter(meetingListByDate, this);
        binding.recyclerView.setAdapter(meetingRecyclerViewAdapter);
    }

    public void configDatePicker() {
        DialogFragment datePicker = new DatePickerFragment();
        datePicker.show(getSupportFragmentManager(), "date picker");
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        currentDate = DateFormat.getDateInstance(DateFormat.DEFAULT).format(calendar.getTime());
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void filterByRoom(String room) {
        initRecyclerView();
        meetingListByRoom = meetingList.stream().filter(meeting -> meeting.getRoom().equals(room)).collect(Collectors.toList());
        meetingRecyclerViewAdapter = new MeetingRecyclerViewAdapter(meetingListByRoom, this);
        binding.recyclerView.setAdapter(meetingRecyclerViewAdapter);
    }

    @Override
    public void onDeleteClick(int position) {
        String descriptionDialog = "\n' " + meetingList.get(position).getTopic() + " - " + meetingList.get(position).getTime() + " - " + meetingList.get(position).getRoom() + " '";
        AlertDialog.Builder myDialog = new AlertDialog.Builder(this);
        myDialog.setTitle(getString(R.string.delete));
        myDialog.setMessage("Êtes-vous sûr de vouloir supprimer :" + descriptionDialog + " ?");
        myDialog.setPositiveButton("Supprimer", (dialog, which) -> {
            switch (state) {
                case 0:
                    meetingApiService.deleteMeeting(meetingList.get(position));
                    break;
                case 1:
                    meetingApiService.deleteMeeting(meetingListByDate.get(position));
                    break;
                case 2:
                    meetingApiService.deleteMeeting(meetingListByRoom.get(position));
                    break;
                default:
                    break;
            }
            meetingRecyclerViewAdapter.notifyItemRemoved(position);
        });
        myDialog.setNegativeButton("Cancel", (dialog, which) -> {

        });
        myDialog.show();
    }

    public void configFabAddMeeting() {
        binding.fabAddMeeting.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, AddMeetingActivity.class);
            startActivity(intent);
        });
    }

}
