package com.gallosalocin.mareu.service;

import com.gallosalocin.mareu.model.Meeting;

import java.util.List;

public class FakeMeetingApiService implements MeetingApiService {

    private List<Meeting> meetingList = MeetingGenerator.generateMeetings();


    @Override
    public List<Meeting> getMeetings() {
        return meetingList;
    }

    @Override
    public void createMeeting(Meeting meeting) {
        meetingList.add(meeting);
    }

    @Override
    public void deleteMeeting(Meeting meeting) {
        meetingList.remove(meeting);
    }

}
