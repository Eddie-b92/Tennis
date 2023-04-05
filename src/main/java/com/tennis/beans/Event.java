package com.tennis.beans;

public class Event {
    private Player player;
    private String placement;
    private Tournament tournament;

    public Event(Player player,String placement, Tournament tournament) {
        this.player     = player;
        this.placement  = placement;
        this.tournament = tournament;
    }

    public Player player() {
        return player;
    }

    public String placement() {
        return placement;
    }

    public Tournament tournament() {
        return tournament;
    }

}