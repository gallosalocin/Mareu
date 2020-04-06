package com.gallosalocin.mareu.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.gallosalocin.mareu.model.Meeting;

import java.util.List;

@Dao public interface MeetingDao {

    @Query("SELECT * From Meeting")
    LiveData<List<Meeting>> getAllMeetings();

    @Query("DELETE FROM Meeting")
    void deleteAllMeetings();

    @Insert
    void insertMeeting(Meeting meeting);

    @Update
    void updateMeeting(Meeting meeting);

    @Delete
    void deleteMeeting(Meeting meeting);
}
