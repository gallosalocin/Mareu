package com.gallosalocin.mareu.service;

import com.gallosalocin.mareu.R;
import com.gallosalocin.mareu.model.Meeting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class MeetingGenerator {

    public static List<Meeting> MEETINGLIST = Arrays.asList(//);
            new Meeting(R.drawable.ic_lens_yellow, "Projet 4", "3 avr. 2020", "12h00", "Salle B", "gallos.11@gmail.com"), new Meeting(R.drawable.ic_lens_yellow, "Projet D", "5 avr. 2020", "22h00", "Salle B", "gallos.11@gmail.com"), new Meeting(R.drawable.ic_lens_yellow, "Projet G", "5 avr. 2020", "18h00", "Salle B", "gallos.11@gmail.com"), new Meeting(R.drawable.ic_lens_yellow, "Projet 8", "5 avr. 2020", "09h00", "Salle B", "gallos.11@gmail.com"), new Meeting(R.drawable.ic_lens_yellow, "Projet 99", "5 avr. 2020", "15h00", "Salle B", "gallos.11@gmail.com"), new Meeting(R.drawable.ic_lens_violet, "Projet 4", "5 avr. 2020", "09h00", "Salle I", "gallosalocin@gmail.com"), new Meeting(R.drawable.ic_lens_orange, "Projet 4", "8 avr. 2020", "20h00", "Salle E", "gallosalocin@gmail.com"), new Meeting(R.drawable.ic_lens_orange, "Projet 4", "10 avr. 2020", "15h00", "Salle E", "gallosalocin@gmail.com"), new Meeting(R.drawable.ic_lens_cyan, "Projet 4", "10 avr. 2020", "20h00", "Salle J", "gallosalocin@gmail.com"), new Meeting(R.drawable.ic_lens_cyan, "Projet 4", "8 avr. 2020", "03h00", "Salle J", "gallosalocin@gmail.com, gallosalocin@gmail.com, gallosalocin@gmail.com"));

    static List<Meeting> generateMeetings() {
        return new ArrayList<>(MEETINGLIST);
    }

}