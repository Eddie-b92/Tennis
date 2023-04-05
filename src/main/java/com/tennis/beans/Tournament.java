package com.tennis.beans;

public class Tournament {
    private int id;
    private String name, code;

    public Tournament(int id, String name, String code) {
        this.id = id;
        this.name = name;
        this.code = code;
    }

    public Tournament(String name, String code) {
        this.name = name;
        this.code = code;
    }

    public Tournament(String name) {
        this.name = name;
    }

    public Tournament() {
    }

    public int id() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String name() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String code() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
