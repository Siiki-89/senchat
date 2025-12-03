package com.senchat.model;

public class User extends Person {

    private int id;
    private String nickName;
    private String password;

    public User() {
        super();
    }

    @Override
    public String getPresentation() {
        return "Usuário: " + getNickName();
    }

    public void validar() throws Exception {

        if (this.name == null || this.name.trim().isEmpty()) {
            throw new Exception("Name cannot be empty.");
        }

        if (this.email == null || this.email.trim().isEmpty()) {
            throw new Exception("Email cannot be empty.");
        }

        if (!this.email.contains("@")) {
            throw new Exception("Invalid email format.");
        }

        if (this.nickName == null || this.nickName.trim().isEmpty()) {
            throw new Exception("Nickname cannot be empty.");
        }

        if (this.password == null || this.password.length() < 6) {
            throw new Exception("Password must have at least 6 characters.");
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
}