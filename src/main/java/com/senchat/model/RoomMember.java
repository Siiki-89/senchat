package com.senchat.model;

public class RoomMember {
    private int id;
    private int roomId;
    private int userId;
    private String role; //if admin or member

    public RoomMember() {
    }

    public RoomMember(int id, int roomId, int userId, String role) {
        this.id = id;
        this.roomId = roomId;
        this.userId = userId;
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
