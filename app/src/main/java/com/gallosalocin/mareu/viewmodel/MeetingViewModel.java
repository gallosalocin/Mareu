package com.gallosalocin.mareu.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.gallosalocin.mareu.model.Meeting;
import com.gallosalocin.mareu.repositories.MeetingRepository;

import java.util.List;

public class MeetingViewModel extends AndroidViewModel {

    private MeetingRepository meetingRepository;
    private LiveData<List<Meeting>> allMeetings;


    public MeetingViewModel(@NonNull Application application) {
        super(application);
        meetingRepository = new MeetingRepository(application);
        allMeetings = meetingRepository.getAllMeetings();
    }

    public LiveData<List<Meeting>> getAllMeetings() { return allMeetings; }

    public void insertMeeting(Meeting meeting) { meetingRepository.insertMeeting(meeting);}

    public void updateMeeting(Meeting meeting) { meetingRepository.updateMeeting(meeting);}

    public void deleteMeeting(Meeting meeting) { meetingRepository.deleteMeeting(meeting);}


}
