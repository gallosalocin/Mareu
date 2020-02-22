package com.gallosalocin.mareu.model;

import org.parceler.Parcel;

@Parcel public class Meeting {

    String topic, time, room, email;

    public Meeting() {
    }

    public Meeting(String topic, String time, String room, String email) {
        this.topic = topic;
        this.time = time;
        this.room = room;
        this.email = email;
    }

    public String getRoom() {
        return room;
    }

    public void setLieu(String room) {
        this.room = room;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
