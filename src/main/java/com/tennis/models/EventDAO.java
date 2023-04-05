package com.tennis.models;

import com.tennis.beans.Event;

import java.util.ArrayList;

public interface EventDAO {
    ArrayList<Event> getAll();
    ArrayList<Event> getEventsByYear(int year);
    ArrayList<Event> getEventsByGender(String gender);
    ArrayList<Event> search(int year, String gender);
}
