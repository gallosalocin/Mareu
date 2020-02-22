package com.gallosalocin.mareu.service;

import com.gallosalocin.mareu.model.Meeting;

import java.util.List;

public interface MeetingApiService {

    List<Meeting> getMeetings();

    void createMeeting(Meeting meeting);
}
