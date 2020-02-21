package com.gallosalocin.mareu.di;

import com.gallosalocin.mareu.service.FakeReunionApiService;
import com.gallosalocin.mareu.service.ReunionApiService;

public class DI {

    private static ReunionApiService service = new FakeReunionApiService();

    public static ReunionApiService getReunoinApiService(){
        return service;
    }
}
