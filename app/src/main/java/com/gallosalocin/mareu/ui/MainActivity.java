package com.gallosalocin.mareu.ui;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Canvas;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gallosalocin.mareu.R;
import com.gallosalocin.mareu.databinding.ActivityMainBinding;
import com.gallosalocin.mareu.model.Meeting;
import com.gallosalocin.mareu.viewmodel.MeetingViewModel;
import com.google.android.material.snackbar.Snackbar;

import org.parceler.Parcels;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

import static java.util.Comparator.comparing;

public class MainActivity extends AppCompatActivity implements MeetingRecyclerViewAdapter.OnItemClickListener,
        DatePickerDialog.OnDateSetListener {

    private List<Meeting> meetingList;
    private List<Meeting> meetingListByDate;
    private List<Meeting> meetingListByRoom;
    private MeetingRecyclerViewAdapter meetingRecyclerViewAdapter;
    private RecyclerView recyclerView;
    private ActivityMainBinding binding;
    private Calendar calendar = Calendar.getInstance();
    private String currentDate;
    private boolean stateRoom = true;
    private boolean stateTime = true;
    private int stateListChoice = 0;
    private MeetingViewModel meetingViewModel;
    String deletedMeeting = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        setSupportActionBar(binding.toolbar);
        configFabAddMeeting();
        initRecyclerView();
        initSwipeRefreshLayout();
        initSwipe();
    }

    // CONFIGURATION RecyclerView

    private void initRecyclerView() {
        Parcels.unwrap(getIntent().getParcelableExtra("meeting"));
        meetingList = new ArrayList<>();
        meetingRecyclerViewAdapter = new MeetingRecyclerViewAdapter(meetingList, this, getApplicationContext());
        binding.recyclerviewMain.setAdapter(meetingRecyclerViewAdapter);
        binding.recyclerviewMain.setLayoutManager(new LinearLayoutManager(this));
        meetingViewModel = new ViewModelProvider(this).get(MeetingViewModel.class);
        meetingViewModel.getAllMeetings().observe(this, meetings -> {
            meetingList = meetings;
            if (meetingList.size() == 0) {
                binding.tvNoMeeting.setVisibility(View.VISIBLE);
                binding.recyclerviewMain.setVisibility(View.GONE);
            } else {
                binding.tvNoMeeting.setVisibility(View.GONE);
                binding.recyclerviewMain.setVisibility(View.VISIBLE);
            }
            meetingRecyclerViewAdapter.setMeetings(meetingList);
        });
    }

    // CONFIGURATION SwipeRefreshLayout

    private void initSwipeRefreshLayout() {
        binding.swipeRefreshLayout.setOnRefreshListener(() -> {
            initRecyclerView();
            stateListChoice = 0;
            binding.swipeRefreshLayout.setRefreshing(false);
        });
    }

    // CONFIGURATION Swipe

    private void initSwipe() {
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder,
                                  @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                deletedMeeting = getString(R.string.alert_dialog_question,
                        meetingRecyclerViewAdapter.getMeetingAt(viewHolder.getAdapterPosition()).getTopic(),
                        meetingRecyclerViewAdapter.getMeetingAt(viewHolder.getAdapterPosition()).getTime(),
                        meetingRecyclerViewAdapter.getMeetingAt(viewHolder.getAdapterPosition()).getRoom());

                meetingViewModel.deleteMeeting(meetingRecyclerViewAdapter.getMeetingAt(viewHolder.getAdapterPosition()));
                Snackbar.make(binding.recyclerviewMain, deletedMeeting, Snackbar.LENGTH_LONG).setAction("Undo",
                        view -> {
                    meetingViewModel.insertMeeting(meetingRecyclerViewAdapter.getMeetingAt(viewHolder.getAdapterPosition()));
                    meetingRecyclerViewAdapter.notifyDataSetChanged();
                }).show();
            }

            @Override
            public void onChildDraw(@NonNull Canvas canvas, @NonNull RecyclerView recyclerView,
                                    @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState,
                                    boolean isCurrentlyActive) {
                new RecyclerViewSwipeDecorator.Builder(canvas, recyclerView, viewHolder, dX, dY, actionState,
                        isCurrentlyActive).addBackgroundColor(ContextCompat.getColor(MainActivity.this,
                        R.color.colorAccent)).addActionIcon(R.drawable.ic_delete_white).create().decorate();
                super.onChildDraw(canvas, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        }).attachToRecyclerView(binding.recyclerviewMain);
    }

    // CONFIGURATION OptionsMenu

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
                stateListChoice = 0;
                initRecyclerView();
                return true;
            case R.id.item_toolbar_sort_name:
                sortListByRoom();
                return true;
            case R.id.item_toolbar_sort_time:
                sortListByTime();
                return true;
            case R.id.item_toolbar_filter_date:
                configDatePicker();
                return true;
            case R.id.subitem_room_a:
                filterByRoom("Salle A");
                return true;
            case R.id.subitem_room_b:
                filterByRoom("Salle B");
                return true;
            case R.id.subitem_room_c:
                filterByRoom("Salle C");
                return true;
            case R.id.subitem_room_d:
                filterByRoom("Salle D");
                return true;
            case R.id.subitem_room_e:
                filterByRoom("Salle E");
                return true;
            case R.id.subitem_room_f:
                filterByRoom("Salle F");
                return true;
            case R.id.subitem_room_g:
                filterByRoom("Salle G");
                return true;
            case R.id.subitem_room_h:
                filterByRoom("Salle H");
                return true;
            case R.id.subitem_room_i:
                filterByRoom("Salle I");
                return true;
            case R.id.subitem_room_j:
                filterByRoom("Salle J");
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // CONFIGURATION sort by room

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void sortListByRoom() {
        switch (stateListChoice) {
            case 0:
                binding.recyclerviewMain.setAdapter(meetingRecyclerViewAdapter);
                meetingList.sort(stateRoom ? comparing(Meeting::getRoom) : comparing(Meeting::getRoom).reversed());
                break;
            case 1:
                binding.recyclerviewMain.setAdapter(meetingRecyclerViewAdapter);
                meetingListByDate.sort(stateRoom ? comparing(Meeting::getRoom) :
                        comparing(Meeting::getRoom).reversed());
                break;
            case 2:
                binding.recyclerviewMain.setAdapter(meetingRecyclerViewAdapter);
                meetingListByRoom.sort(stateRoom ? comparing(Meeting::getRoom) :
                        comparing(Meeting::getRoom).reversed());
                break;
            default:
                break;
        }
        stateRoom = !stateRoom;
    }

    // CONFIGURATION sort by time

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void sortListByTime() {
        switch (stateListChoice) {
            case 0:
                binding.recyclerviewMain.setAdapter(meetingRecyclerViewAdapter);
                meetingList.sort(stateTime ? comparing(Meeting::getTime) : comparing(Meeting::getTime).reversed());
                break;
            case 1:
                binding.recyclerviewMain.setAdapter(meetingRecyclerViewAdapter);
                meetingListByDate.sort(stateTime ? comparing(Meeting::getTime) :
                        comparing(Meeting::getTime).reversed());
                break;
            case 2:
                binding.recyclerviewMain.setAdapter(meetingRecyclerViewAdapter);
                meetingListByRoom.sort(stateTime ? comparing(Meeting::getTime) :
                        comparing(Meeting::getTime).reversed());
                break;
            default:
                break;
        }
        stateTime = !stateTime;
    }

    // CONFIGURATION filter by date

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void filterByDate() {
        stateListChoice = 1;
        meetingListByDate =
                meetingList.stream().filter(meeting -> meeting.getDate().equals(currentDate)).collect(Collectors.toList());
        meetingRecyclerViewAdapter = new MeetingRecyclerViewAdapter(meetingListByDate, this, getApplicationContext());
        if (meetingListByDate.size() == 0) {
            binding.tvNoMeeting.setVisibility(View.VISIBLE);
            binding.recyclerviewMain.setVisibility(View.GONE);
        } else {
            binding.tvNoMeeting.setVisibility(View.GONE);
            binding.recyclerviewMain.setVisibility(View.VISIBLE);
        }
        binding.recyclerviewMain.setAdapter(meetingRecyclerViewAdapter);
    }

    private void configDatePicker() {
        DatePickerDialog datePicker = new DatePickerDialog(this, this, calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePicker.show();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        currentDate = DateFormat.getDateInstance(DateFormat.DEFAULT).format(calendar.getTime());
        filterByDate();
    }

    // CONFIGURATION filter by room

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void filterByRoom(String room) {
        stateListChoice = 2;
        meetingListByRoom =
                meetingList.stream().filter(meeting -> meeting.getRoom().equals(room)).collect(Collectors.toList());
        meetingRecyclerViewAdapter = new MeetingRecyclerViewAdapter(meetingListByRoom, this, getApplicationContext());
        if (meetingListByRoom.size() == 0) {
            binding.tvNoMeeting.setVisibility(View.VISIBLE);
            binding.recyclerviewMain.setVisibility(View.GONE);
        } else {
            binding.tvNoMeeting.setVisibility(View.GONE);
            binding.recyclerviewMain.setVisibility(View.VISIBLE);
        }
        binding.recyclerviewMain.setAdapter(meetingRecyclerViewAdapter);
    }

    // CONFIGURATION delete button

    @Override
    public void onDeleteClick(int position) {
        AlertDialog.Builder myDialog = new AlertDialog.Builder(this);
        myDialog.setCancelable(false);
        myDialog.setTitle(getString(R.string.delete));
        myDialog.setMessage(getString(R.string.alert_dialog_question, meetingList.get(position).getTopic(),
                meetingList.get(position).getTime(), meetingList.get(position).getRoom()));
        myDialog.setPositiveButton(getString(R.string.delete), (dialog, which) -> {
            switch (stateListChoice) {
                case 0:
                    meetingViewModel.deleteMeeting(meetingList.get(position));
                    break;
                case 1:
                    meetingViewModel.deleteMeeting(meetingListByDate.get(position));
                    break;
                case 2:
                    meetingViewModel.deleteMeeting(meetingListByRoom.get(position));
                    break;
                default:
                    break;
            }
            meetingRecyclerViewAdapter.notifyItemRemoved(position);
            initRecyclerView();
            stateListChoice = 0;
        });
        myDialog.setNegativeButton(getString(R.string.cancel), (dialog, which) -> {
        });
        myDialog.show();
    }

    // CONFIGURATION fab add meeting

    public void configFabAddMeeting() {
        binding.fabAddMeeting.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, AddMeetingActivity.class);
            startActivity(intent);
        });
    }
}
