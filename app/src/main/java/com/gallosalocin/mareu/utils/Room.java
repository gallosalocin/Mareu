package com.gallosalocin.mareu.utils;

import androidx.annotation.NonNull;

public class Room {

    private String room;
    private int roomColor;

    public Room() {
    }

    public Room(String room, int roomColor) {
        this.room = room;
        this.roomColor = roomColor;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public int getRoomColor() {
        return roomColor;
    }

    public void setRoomColor(int roomColor) {
        this.roomColor = roomColor;
    }

    @NonNull
    @Override
    public String toString() {
        return room;
    }
}
