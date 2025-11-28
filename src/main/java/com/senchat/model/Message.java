package com.senchat.model;

import java.time.LocalDateTime;

public class Message {
    private int id;
    private int userId;
    private int roomId;
    private String content;
    private LocalDateTime sent;

    public Message() {
    }

    public Message(int id, int userId, int roomId, String content, LocalDateTime sent) {
        this.id = id;
        this.userId = userId;
        this.roomId = roomId;
        this.content = content;
        this.sent = sent;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getSent() {
        return sent;
    }

    public void setSent(LocalDateTime sent) {
        this.sent = sent;
    }
}
