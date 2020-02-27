package com.gallosalocin.mareu.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.gallosalocin.mareu.R;
import com.gallosalocin.mareu.databinding.ActivityMainBinding;
import com.gallosalocin.mareu.di.DI;
import com.gallosalocin.mareu.model.Meeting;
import com.gallosalocin.mareu.service.MeetingApiService;

import org.parceler.Parcels;

import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MeetingRecyclerViewAdapter.OnItemClickListener {

    private List<Meeting> meetingList;
    private MeetingRecyclerViewAdapter meetingRecyclerViewAdapter;

    private ActivityMainBinding binding;

    MeetingApiService meetingApiService;


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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.subitem_toolbar_sort_name:
                sortListByRoom();
                return true;
            case R.id.subitem_toolbar_sort_time:
                sortListByTime();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void sortListByRoom() {
        Collections.sort(meetingList, (o1, o2) -> {
            initRecyclerView();
            return o1.getRoom().compareTo(o2.getRoom());
        });
    }

    public void sortListByTime() {
        Collections.sort(meetingList, (o1, o2) -> {
            initRecyclerView();
            return o1.getTime().compareTo(o2.getTime());
        });
    }

    @Override
    public void onDeleteClick(int position) {
        meetingList.remove(position);
        meetingRecyclerViewAdapter.notifyItemRemoved(position);
    }

    public void configFabAddMeeting() {
        binding.fabAddMeeting.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, AddMeetingActivity.class);
            startActivity(intent);
        });
    }
}
