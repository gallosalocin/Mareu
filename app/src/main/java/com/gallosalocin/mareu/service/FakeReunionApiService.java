package com.gallosalocin.mareu.service;

import com.gallosalocin.mareu.model.Reunion;

import java.util.List;

public class FakeReunionApiService implements ReunionApiService{

    private List<Reunion> reunionList = ReunionGenerator.generateReunions();


    @Override
    public List<Reunion> getReunions() {
        return reunionList;
    }
}
