package com.gallosalocin.mareu.service;

import com.gallosalocin.mareu.R;
import com.gallosalocin.mareu.model.Meeting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class MeetingGenerator {

    public static List<Meeting> MEETINGLIST = Arrays.asList(new Meeting(R.drawable.ic_lens_yellow, "Projet 4", "12h00", "Room J", "gallosalocin@gmail.com"));

    static List<Meeting> generateMeetings() {
        return new ArrayList<>(MEETINGLIST);
    }

}
