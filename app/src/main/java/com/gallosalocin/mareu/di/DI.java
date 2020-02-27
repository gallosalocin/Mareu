package com.gallosalocin.mareu.di;

import com.gallosalocin.mareu.service.FakeMeetingApiService;
import com.gallosalocin.mareu.service.MeetingApiService;

public class DI {

    private static MeetingApiService service = new FakeMeetingApiService();

    public static MeetingApiService getMeetingApiService() {
        return service;
    }

    public static MeetingApiService getNewInstanceApiService() {
        return new FakeMeetingApiService();
    }
}
