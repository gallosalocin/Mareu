//package com.gallosalocin.mareu;
//
//import android.app.Application;
//import android.content.Context;
//
//import androidx.lifecycle.LiveData;
//import androidx.room.Room;
//
//import com.gallosalocin.mareu.database.MeetingDao;
//import com.gallosalocin.mareu.database.MeetingRoomDatabase;
//import com.gallosalocin.mareu.model.Meeting;
//
//import org.junit.After;
//import org.junit.Before;
//import org.junit.FixMethodOrder;
//import org.junit.Rule;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.junit.runners.JUnit4;
//import org.junit.runners.MethodSorters;
//
//import java.util.List;
//
//import static junit.framework.TestCase.assertEquals;
//
//
///**
// * Example local unit test, which will execute on the development machine (host).
// *
// * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
// */
//@FixMethodOrder(MethodSorters.NAME_ASCENDING) @RunWith(JUnit4.class) public class ExampleUnitTest {
//
//    @Rule
//    private MeetingDao meetingDao;
//    private MeetingRoomDatabase meetingRoomDatabase;
//
//    @Before
//    public void createDataBase() {
//        Context context = Application.getApplicationContext();
//        // Using an in-memory database because the information stored here disappears when the
//        // process is killed.
//        meetingRoomDatabase = Room.inMemoryDatabaseBuilder(context, MeetingRoomDatabase.class)
//                // Allowing main thread queries, just for testing.
//                .allowMainThreadQueries().build();
//        meetingDao = meetingRoomDatabase.meetingDao();
//    }
//
//    @After
//    public void closeDb() {
//        meetingRoomDatabase.close();
//    }
//
//    @Test
//    public void getAllMeetings() {
//        Meeting meeting = new Meeting(R.drawable.ic_lens_yellow, "Projet 4", "3 avr. 2020", "12h00", "Salle B",
//                "gallos.11@gmail.com");
//        meetingDao.insertMeeting(meeting);
//        Meeting meeting1 = new Meeting(R.drawable.ic_lens_violet, "Projet 4", "5 avr. 2020", "09h00",
//                "Salle I", "gallosalocin@gmail.com");
//        meetingDao.insertMeeting(meeting1);
//        List<Meeting> allWords = LiveData.getValue(meetingDao.getAllMeetings());
//        assertEquals(allWords.get(0).getRoom(), meeting.getRoom());
//        assertEquals(allWords.get(1).getRoom(), meeting1.getRoom());
//    }
//
//    @Test
//    public void deleteMeetingWithSuccess() {
//    }
//
//    @Test
//    public void createMeetingWithSuccess() {
//    }
//}