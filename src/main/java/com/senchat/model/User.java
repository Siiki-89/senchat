package com.senchat.model;

public class User {
    private int id;
    private String name;
    private String nickName;
    private String password;
    private String email;

    public User() {
    }

    public User(int userId, String name, String nickName, String password, String email) {
        this.id = userId;
        this.name = name;
        this.nickName = nickName;
        this.password = password;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
