package com.gallosalocin.mareu.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gallosalocin.mareu.R;
import com.gallosalocin.mareu.model.Reunion;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements ReunionRecycleViewAdapter.OnItemClickListener {

    private static final String TAG = "MainActivity";

    @BindView(R.id.recyclerview_main_reunion)
    RecyclerView recyclerView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.fab_main_add_reunion)
    FloatingActionButton fabAddReunion;

    private List<Reunion> reunionList;
    private ReunionRecycleViewAdapter reunionRecycleViewAdapter;

    //    ReunionApiService reunionApiService;                            // new

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        Log.d(TAG, "onCreate: started.");
        configFabAddReunion();
        createReunionsList();
        initRecyclerView();
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
        Collections.sort(reunionList, new Comparator<Reunion>() {
            @Override
            public int compare(Reunion o1, Reunion o2) {
                initRecyclerView();
                return o1.getRoom().compareTo(o2.getRoom());
            }
        });
    }

    public void sortListByTime() {
        Collections.sort(reunionList, new Comparator<Reunion>() {
            @Override
            public int compare(Reunion o1, Reunion o2) {
                initRecyclerView();
                return o1.getTime().compareTo(o2.getTime());
            }
        });
    }

    private void initRecyclerView() {
        reunionRecycleViewAdapter = new ReunionRecycleViewAdapter(reunionList, this);
        recyclerView.setAdapter(reunionRecycleViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void createReunionsList() {
        reunionList = new ArrayList<>();
        reunionList.add(new Reunion("Reunion AAA", "10h00", "Salle C", "maxime@lamzone.com, alex@lamzone.com, nicolas@gmail.com"));
        reunionList.add(new Reunion("Reunion CCC", "17h00", "Salle B", "maxime@lamzone.com, alex@lamzone.com, nicolas@gmail.com"));
        reunionList.add(new Reunion("Reunion BBB", "14h00", "Salle A", "maxime@lamzone.com, alex@lamzone.com, nicolas@gmail.com"));
    }

    @Override
    public void onDeleteClick(int position) {
        reunionList.remove(position);
        reunionRecycleViewAdapter.notifyItemRemoved(position);
    }

    public void configFabAddReunion() {
        fabAddReunion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddReunionActivity.class);
                startActivity(intent);
            }
        });
    }

}
