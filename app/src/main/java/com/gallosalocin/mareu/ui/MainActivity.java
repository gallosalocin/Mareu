package com.gallosalocin.mareu.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gallosalocin.mareu.R;
import com.gallosalocin.mareu.di.DI;
import com.gallosalocin.mareu.model.Meeting;
import com.gallosalocin.mareu.service.MeetingApiService;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.parceler.Parcels;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements MeetingRecycleViewAdapter.OnItemClickListener {

    //    private static final String TAG = "MainActivity";

    @BindView(R.id.recyclerview_main_meeting)
    RecyclerView recyclerView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.fab_main_add_meeting)
    FloatingActionButton fabAddMeeting;

    private List<Meeting> meetingList;
    private Meeting meeting;
    private MeetingRecycleViewAdapter meetingRecycleViewAdapter;

    MeetingApiService meetingApiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        meetingApiService = DI.getMeetingApiService();
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        configFabAddMeeting();
        initRecyclerView();
    }

    private void initRecyclerView() {
        meeting = Parcels.unwrap(getIntent().getParcelableExtra("meeting"));
        meetingList = meetingApiService.getMeetings();
        meetingRecycleViewAdapter = new MeetingRecycleViewAdapter(meetingList, this);
        recyclerView.setAdapter(meetingRecycleViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
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
        Collections.sort(meetingList, new Comparator<Meeting>() {
            @Override
            public int compare(Meeting o1, Meeting o2) {
                initRecyclerView();
                return o1.getRoom().compareTo(o2.getRoom());
            }
        });
    }

    public void sortListByTime() {
        Collections.sort(meetingList, new Comparator<Meeting>() {
            @Override
            public int compare(Meeting o1, Meeting o2) {
                initRecyclerView();
                return o1.getTime().compareTo(o2.getTime());
            }
        });
    }

    @Override
    public void onDeleteClick(int position) {
        meetingList.remove(position);
        meetingRecycleViewAdapter.notifyItemRemoved(position);
    }

    public void configFabAddMeeting() {
        fabAddMeeting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddMeetingActivity.class);
                startActivity(intent);
            }
        });
    }

}
