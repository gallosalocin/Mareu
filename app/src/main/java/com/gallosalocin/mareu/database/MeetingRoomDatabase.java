package com.gallosalocin.mareu.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.gallosalocin.mareu.R;
import com.gallosalocin.mareu.model.Meeting;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Meeting.class}, version = 1, exportSchema = false)
public abstract class MeetingRoomDatabase extends RoomDatabase {

    public abstract MeetingDao meetingDao();

    private static volatile MeetingRoomDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static MeetingRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (MeetingRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), MeetingRoomDatabase.class,
                            "meeting_database").addCallback(populateDatabase).build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback populateDatabase = new RoomDatabase.Callback() {

        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            databaseWriteExecutor.execute(() -> {
                MeetingDao meetingDao = INSTANCE.meetingDao();

                meetingDao.insertMeeting(new Meeting(R.drawable.ic_lens_yellow, "Projet 4", "3 avr. 2020", "12h00",
                        "Salle B", "gallos.11@gmail.com"));
                meetingDao.insertMeeting(new Meeting(R.drawable.ic_lens_violet, "Projet 4", "5 avr. 2020", "09h00",
                        "Salle I", "gallosalocin@gmail.com"));
                meetingDao.insertMeeting(new Meeting(R.drawable.ic_lens_cyan, "Projet 4", "8 avr. 2020", "03h00",
                        "Salle J", "gallosalocin@gmail.com, gallosalocin@gmail.com, gallosalocin@gmail.com"));
            });
        }
    };
}
