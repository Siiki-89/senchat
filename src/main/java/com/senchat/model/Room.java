package com.senchat.model;

public class Room {
    private int id;
    private String topic;
    private String sector;
    private int adminId;

    public Room() {
    }

    public int getRoomId() {
        return id;
    }

    public void setRoomId(int roomId) {
        this.id = roomId;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public int getAdminId() {
        return adminId;
    }

    public void setAdminId(int adminId) {
        this.adminId = adminId;
    }
}
