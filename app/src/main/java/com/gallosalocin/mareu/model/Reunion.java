package com.gallosalocin.mareu.model;

public class Reunion {

    private String topic, time, room, email;


    public Reunion(String topic, String time, String room, String email) {
        this.topic = topic;
        this.time = time;
        this.room = room;
        this.email = email;
    }

    public Reunion(String topic, String email) {
        this.topic = topic;
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
