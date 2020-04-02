package com.gallosalocin.mareu.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.gallosalocin.mareu.database.MeetingDao;
import com.gallosalocin.mareu.database.MeetingRoomDatabase;
import com.gallosalocin.mareu.model.Meeting;

import java.util.List;

public class MeetingRepository {

    private MeetingDao meetingDao;
    private LiveData<List<Meeting>> allMeetings;

    public MeetingRepository(Application application) {
        MeetingRoomDatabase db = MeetingRoomDatabase.getDatabase(application);
        meetingDao = db.meetingDao();
        allMeetings = meetingDao.getAllMeetings();
    }

    public void insertMeeting(Meeting meeting) {
        MeetingRoomDatabase.databaseWriteExecutor.execute(() -> {
            meetingDao.insertMeeting(meeting);
        });
    }

    public void updateMeeting(Meeting meeting) {
        MeetingRoomDatabase.databaseWriteExecutor.execute(() -> {
            meetingDao.updateMeeting(meeting);
        });
    }

    public void deleteMeeting(Meeting meeting) {
        MeetingRoomDatabase.databaseWriteExecutor.execute(() -> {
            meetingDao.deleteMeeting(meeting);
        });
    }

    public LiveData<List<Meeting>> getAllMeetings() {
        return allMeetings;
    }
}
