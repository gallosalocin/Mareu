package com.gallosalocin.mareu.service;

import com.gallosalocin.mareu.model.Meeting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class MeetingGenerator {

    public static List<Meeting> MEETINGLIST = Arrays.asList();

    static List<Meeting> generateMeetings() {
        return new ArrayList<>(MEETINGLIST);
    }

}