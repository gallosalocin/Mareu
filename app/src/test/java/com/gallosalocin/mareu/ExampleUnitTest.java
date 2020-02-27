package com.gallosalocin.mareu;

import com.gallosalocin.mareu.di.DI;
import com.gallosalocin.mareu.model.Meeting;
import com.gallosalocin.mareu.service.MeetingApiService;
import com.gallosalocin.mareu.service.MeetingGenerator;

import org.hamcrest.collection.IsIterableContainingInAnyOrder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(JUnit4.class) public class ExampleUnitTest {

    private MeetingApiService service;

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
        Meeting meeting = service.getMeetings().get(0);

    }

    @Test
    public void createMeetingWithSuccess() {
        Meeting meeting = service.getMeetings().get(0);
        service.getMeetings().clear();
        service.createMeeting(meeting);
        Assert.assertEquals(1, service.getMeetings().size());
        Assert.assertTrue(MeetingGenerator.MEETINGLIST.stream().map(Meeting::getRoomColor).collect(Collectors.toList()).contains(meeting.getRoomColor()));
        Assert.assertTrue(MeetingGenerator.MEETINGLIST.stream().map(Meeting::getRoom).collect(Collectors.toList()).contains(meeting.getRoom()));
        Assert.assertTrue(MeetingGenerator.MEETINGLIST.stream().map(Meeting::getTime).collect(Collectors.toList()).contains(meeting.getTime()));
        Assert.assertTrue(MeetingGenerator.MEETINGLIST.stream().map(Meeting::getTopic).collect(Collectors.toList()).contains(meeting.getTopic()));
        Assert.assertTrue(MeetingGenerator.MEETINGLIST.stream().map(Meeting::getEmail).collect(Collectors.toList()).contains(meeting.getEmail()));
    }
}