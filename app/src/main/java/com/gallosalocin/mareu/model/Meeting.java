package com.gallosalocin.mareu.model;

import org.parceler.Parcel;

@Parcel public class Meeting {

    String topic, date, time, room, email;
    int roomColor;

    public Meeting() {
    }

    public Meeting(int roomColor, String topic, String date, String time, String room, String email) {
        this.roomColor = roomColor;
        this.topic = topic;
        this.time = time;
        this.room = room;
        this.email = email;
        this.date = date;
    }

    public int getRoomColor() {
        return roomColor;
    }

    public void setRoomColor(int roomColor) {
        this.roomColor = roomColor;
    }

    public String getRoom() {
        return room;
    }

    public void setLieu(String room) {
        this.room = room;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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
