package com.tennis.beans;

public class User {

    private long id;
    private int profile;

    private String username;

    private String password;

    public User(long id, String username, String password, int profile) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.profile = profile;
    }

    public User(String username, String password, int profile) {
        this.username = username;
        this.password = password;
        this.profile = profile;
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public int profile() {
        return profile;
    }

    public void setProfile(int profile) {
        this.profile = profile;
    }

    public long id() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String username() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String password() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
