package com.gallosalocin.mareu;

import com.gallosalocin.mareu.di.DI;
import com.gallosalocin.mareu.model.Meeting;
import com.gallosalocin.mareu.service.MeetingApiService;
import com.gallosalocin.mareu.service.MeetingGenerator;

import org.hamcrest.collection.IsIterableContainingInAnyOrder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.junit.runners.MethodSorters;

import java.util.List;
import java.util.Objects;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertFalse;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING) @RunWith(JUnit4.class) public class ExampleUnitTest {

    private MeetingApiService service;
    private Meeting meeting = new Meeting(R.drawable.ic_lens_yellow, "Projet 4", "3 avr. 2020", "12h00", "Room B", "gallos@gmail.com");


    @Before
    public void setup() {
        service = DI.getNewInstanceApiService();
    }

    @Test
    public void getMeetingWithSuccess() {
        List<Meeting> meetingList = service.getMeetings();
        List<Meeting> expectedMeetingList = MeetingGenerator.MEETINGLIST;
        assertThat(meetingList, IsIterableContainingInAnyOrder.containsInAnyOrder(Objects.requireNonNull(expectedMeetingList.toArray())));
    }

    @Test
    public void deleteMeetingWithSuccess() {
        service.deleteMeeting(meeting);
        assertFalse(service.getMeetings().contains(meeting));
    }

    @Test
    public void createMeetingWithSuccess() {
        service.createMeeting(meeting);
        Assert.assertEquals(1, service.getMeetings().size());
        Assert.assertTrue(service.getMeetings().contains(meeting));
    }
}