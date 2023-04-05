package com.tennis.beans;

public class Player {
    private int id;
    private String firstName, lastName, gender;

    public Player(int id, String firstName, String lastName, String gender) {
        this.id        = id;
        this.firstName = firstName;
        this.lastName  = lastName;
        this.gender    = gender;
    }

    public Player(String firstName, String lastName, String gender) {
        this.firstName = firstName;
        this.lastName  = lastName;
        this.gender    = gender;
    }

    public Player(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName  = lastName;
    }

    public Player(int id) {
        this.id = id;
    }

    public Player() {
    }

    @Override
    public String toString() {
        return firstName() +" "+ lastName() +" "+ gender();
    }

    public int id() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String firstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String lastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String gender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
