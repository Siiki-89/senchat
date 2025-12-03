package com.senchat.model;

public abstract class Person {
    protected String name;
    protected String email;

    public Person() {
    }

    public String getPresentation() {
        return "Pessoa: " + name;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}