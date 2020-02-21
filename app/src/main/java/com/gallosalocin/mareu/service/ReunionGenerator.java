package com.gallosalocin.mareu.service;

import com.gallosalocin.mareu.model.Reunion;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class ReunionGenerator {

    public static List<Reunion> REUNIONLIST = Arrays.asList(
        new Reunion("Reunion AAA", "10h00", "Salle C", "maxime@lamzone.com, alex@lamzone.com, nicolas@gmail.com"),
        new Reunion("Reunion CCC", "17h00", "Salle B", "maxime@lamzone.com, alex@lamzone.com, nicolas@gmail.com"),
        new Reunion("Reunion BBB", "14h00", "Salle A", "maxime@lamzone.com, alex@lamzone.com, nicolas@gmail.com"));

    static List<Reunion> generateReunions(){
        return new ArrayList<>(REUNIONLIST);
    }

}
